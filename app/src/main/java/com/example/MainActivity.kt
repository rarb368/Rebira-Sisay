package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.data.ExamDataManager
import com.example.data.QuestionRepository
import com.example.model.ActiveExamSession
import com.example.model.ExamAttempt
import com.example.model.ExamSubject
import com.example.model.Question
import com.example.model.StudentProfile
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

enum class Screen {
    REGISTRATION,
    DASHBOARD,
    SUBJECT_EXAMS,
    EXAM,
    EXAM_RESULT,
    CONTACT_US,
    ADMIN
}

class MainActivity : ComponentActivity() {

    private lateinit var dataManager: ExamDataManager
    private lateinit var questionRepository: QuestionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dataManager = ExamDataManager(applicationContext)
        questionRepository = QuestionRepository()
        val offlineCacheManager = com.example.data.OfflineCacheManager(applicationContext)
        offlineCacheManager.ensureOfflineCacheReady(questionRepository.getAllQuestions())

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigationRoot(
                        dataManager = dataManager,
                        questionRepository = questionRepository
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigationRoot(
    dataManager: ExamDataManager,
    questionRepository: QuestionRepository
) {
    // Current student profile
    var studentProfile by remember { mutableStateOf(dataManager.getSavedProfile()) }

    // Active unfinished exam session (if any)
    var activeSession by remember { mutableStateOf(dataManager.getActiveSession()) }

    // Screen navigation stack/state
    var currentScreen by remember {
        mutableStateOf(if (studentProfile == null) Screen.REGISTRATION else Screen.DASHBOARD)
    }

    // Navigation payload state
    var selectedSubject by remember { mutableStateOf(ExamSubject.BIOLOGY) }

    // Exam runtime parameters
    var examQuestions by remember { mutableStateOf<List<Question>>(emptyList()) }
    var examSubjectTitle by remember { mutableStateOf("") }
    var examYear by remember { mutableStateOf("2017") }
    var examIsUnitPractice by remember { mutableStateOf(false) }
    var examGrade by remember { mutableIntStateOf(12) }
    var examUnitTitle by remember { mutableStateOf("") }
    var examRevealMode by remember { mutableStateOf("right_away") }
    var examRemainingSeconds by remember { mutableLongStateOf(7200L) }
    var examInitialAnswers by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }

    // Latest finished attempt
    var latestAttempt by remember { mutableStateOf<ExamAttempt?>(null) }

    // Handle system back button per screen
    when (currentScreen) {
        Screen.SUBJECT_EXAMS -> {
            BackHandler {
                currentScreen = Screen.DASHBOARD
            }
        }
        Screen.CONTACT_US, Screen.ADMIN -> {
            BackHandler {
                currentScreen = Screen.DASHBOARD
            }
        }
        Screen.EXAM_RESULT -> {
            BackHandler {
                currentScreen = Screen.DASHBOARD
            }
        }
        else -> Unit
    }

    when (currentScreen) {
        Screen.REGISTRATION -> {
            RegistrationScreen(
                onRegistered = { profile ->
                    dataManager.saveProfile(profile)
                    studentProfile = profile
                    currentScreen = Screen.DASHBOARD
                }
            )
        }

        Screen.DASHBOARD -> {
            val profile = studentProfile ?: StudentProfile(fullName = "AIBSBSS Student")
            DashboardScreen(
                studentProfile = profile,
                activeSession = activeSession,
                totalCachedQuestions = questionRepository.getAllQuestions().size,
                onSubjectClick = { subject ->
                    selectedSubject = subject
                    currentScreen = Screen.SUBJECT_EXAMS
                },
                onResumeSession = {
                    val session = activeSession
                    if (session != null) {
                        val questions = if (session.isUnitPractice) {
                            questionRepository.getQuestionsForUnit(session.subject, session.grade, session.unitTitle, session.year)
                        } else {
                            questionRepository.getQuestionsForFullExam(session.subject, session.year)
                        }

                        examQuestions = questions
                        examSubjectTitle = session.subject.uppercase()
                        examYear = session.year
                        examIsUnitPractice = session.isUnitPractice
                        examGrade = session.grade
                        examUnitTitle = session.unitTitle
                        examRevealMode = session.revealMode
                        examRemainingSeconds = session.remainingSeconds
                        examInitialAnswers = session.answers

                        currentScreen = Screen.EXAM
                    }
                },
                onDiscardSession = {
                    dataManager.clearActiveSession()
                    activeSession = null
                },
                onNavigateBottom = { tab ->
                    when (tab) {
                        "home" -> currentScreen = Screen.DASHBOARD
                        "contact" -> currentScreen = Screen.CONTACT_US
                        "admin" -> currentScreen = Screen.ADMIN
                    }
                }
            )
        }

        Screen.SUBJECT_EXAMS -> {
            SubjectExamListScreen(
                subject = selectedSubject,
                dataManager = dataManager,
                questionRepository = questionRepository,
                onBack = { currentScreen = Screen.DASHBOARD },
                onStartExam = { year, isUnitPractice, grade, unitTitle, revealMode ->
                    val questions = if (isUnitPractice) {
                        questionRepository.getQuestionsForUnit(selectedSubject.id, grade, unitTitle, year)
                    } else {
                        questionRepository.getQuestionsForFullExam(selectedSubject.id, year)
                    }

                    // Calculate time: 2 minutes per question in unit practice, or subject default duration in full exam
                    val durationSeconds = if (isUnitPractice) {
                        (questions.size.coerceAtLeast(1) * 2 * 60L)
                    } else {
                        (selectedSubject.defaultDurationMinutes * 60L)
                    }

                    examQuestions = questions
                    examSubjectTitle = selectedSubject.title
                    examYear = year
                    examIsUnitPractice = isUnitPractice
                    examGrade = grade
                    examUnitTitle = unitTitle
                    examRevealMode = revealMode
                    examRemainingSeconds = durationSeconds
                    examInitialAnswers = emptyMap()

                    currentScreen = Screen.EXAM
                }
            )
        }

        Screen.EXAM -> {
            val profile = studentProfile ?: StudentProfile(fullName = "AIBSBSS Student")
            ExamScreen(
                questions = examQuestions,
                subjectTitle = examSubjectTitle,
                year = examYear,
                isUnitPractice = examIsUnitPractice,
                grade = examGrade,
                unitTitle = examUnitTitle,
                revealMode = examRevealMode,
                initialRemainingSeconds = examRemainingSeconds,
                initialAnswers = examInitialAnswers,
                studentProfile = profile,
                onSaveAndQuit = { session ->
                    dataManager.saveActiveSession(session)
                    activeSession = session
                    currentScreen = Screen.DASHBOARD
                },
                onSubmitExam = { attempt ->
                    dataManager.saveExamAttempt(attempt)
                    latestAttempt = attempt
                    activeSession = null
                    currentScreen = Screen.EXAM_RESULT
                }
            )
        }

        Screen.EXAM_RESULT -> {
            latestAttempt?.let { attempt ->
                ExamResultScreen(
                    attempt = attempt,
                    onReviewAnswers = {
                        // Open in review mode with all explanations and answers shown
                        examRevealMode = "right_away"
                        examRemainingSeconds = 0L
                        examInitialAnswers = attempt.userAnswers
                        currentScreen = Screen.EXAM
                    },
                    onTryAgain = {
                        // Restart exam with original settings
                        val durationSeconds = if (attempt.isUnitPractice) {
                            (examQuestions.size.coerceAtLeast(1) * 2 * 60L)
                        } else {
                            (selectedSubject.defaultDurationMinutes * 60L)
                        }
                        examRemainingSeconds = durationSeconds
                        examInitialAnswers = emptyMap()
                        currentScreen = Screen.EXAM
                    },
                    onReturnHome = {
                        currentScreen = Screen.DASHBOARD
                    }
                )
            } ?: run {
                currentScreen = Screen.DASHBOARD
            }
        }

        Screen.CONTACT_US -> {
            ContactUsScreen(
                onNavigateBottom = { tab ->
                    when (tab) {
                        "home" -> currentScreen = Screen.DASHBOARD
                        "contact" -> currentScreen = Screen.CONTACT_US
                        "admin" -> currentScreen = Screen.ADMIN
                    }
                }
            )
        }

        Screen.ADMIN -> {
            AdminScreen(
                dataManager = dataManager,
                questionRepository = questionRepository,
                onBack = { currentScreen = Screen.DASHBOARD }
            )
        }
    }
}
