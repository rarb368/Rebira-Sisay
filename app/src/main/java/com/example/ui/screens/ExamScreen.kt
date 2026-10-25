package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ActiveExamSession
import com.example.model.ExamAttempt
import com.example.model.Question
import com.example.model.StudentProfile
import com.example.ui.components.QuitExamConfirmationDialog
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    questions: List<Question>,
    subjectTitle: String,
    year: String,
    isUnitPractice: Boolean,
    grade: Int,
    unitTitle: String,
    revealMode: String, // "right_away" or "after_finishing"
    initialRemainingSeconds: Long,
    initialAnswers: Map<String, Int>,
    studentProfile: StudentProfile,
    onSaveAndQuit: (ActiveExamSession) -> Unit,
    onSubmitExam: (ExamAttempt) -> Unit
) {
    var showQuitDialog by remember { mutableStateOf(false) }
    var showSubmitConfirmDialog by remember { mutableStateOf(false) }

    // User answers map: question.id -> selected option index (0..3)
    val userAnswers = remember { mutableStateMapOf<String, Int>().apply { putAll(initialAnswers) } }

    // Expanded explanations map for toggles
    val expandedExplanations = remember { mutableStateMapOf<String, Boolean>() }

    // Timer management based on deadline timestamp
    var remainingSeconds by remember { mutableLongStateOf(initialRemainingSeconds) }
    val startTimeMillis = remember { System.currentTimeMillis() }
    val totalDurationSeconds = remember { initialRemainingSeconds }

    // Warning alerts
    var hasWarned5Min by remember { mutableStateOf(false) }
    var hasWarned1Min by remember { mutableStateOf(false) }
    var showWarningDialogText by remember { mutableStateOf<String?>(null) }

    // Submission state
    var isSubmitted by remember { mutableStateOf(false) }

    // Countdown timer coroutine loop
    LaunchedEffect(isSubmitted) {
        val targetEndTimeMillis = System.currentTimeMillis() + (remainingSeconds * 1000L)
        while (remainingSeconds > 0 && !isSubmitted) {
            delay(1000L)
            val now = System.currentTimeMillis()
            val left = ((targetEndTimeMillis - now) / 1000L).coerceAtLeast(0L)
            remainingSeconds = left

            if (left <= 300 && !hasWarned5Min && left > 60) {
                hasWarned5Min = true
                showWarningDialogText = "Warning: Only 5 minutes remaining in this examination!"
            } else if (left <= 60 && !hasWarned1Min && left > 0) {
                hasWarned1Min = true
                showWarningDialogText = "Alert: Only 1 minute remaining! Please review your answers."
            }

            if (left <= 0L) {
                // Time up! Auto submit
                isSubmitted = true
                calculateAndSubmitExam(
                    questions = questions,
                    userAnswers = userAnswers,
                    subjectTitle = subjectTitle,
                    year = year,
                    isUnitPractice = isUnitPractice,
                    grade = grade,
                    unitTitle = unitTitle,
                    totalDurationSeconds = totalDurationSeconds,
                    timeRemainingSeconds = 0L,
                    studentProfile = studentProfile,
                    onSubmitExam = onSubmitExam
                )
                break
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (isUnitPractice) "G$grade U${questions.firstOrNull()?.unitNumber ?: 1} : ${questions.size} ques." else "$subjectTitle — $year",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Text(
                            text = "${userAnswers.size}/${questions.size} answered",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                },
                navigationIcon = {
                    // Visible Back / Quit Exam button
                    IconButton(
                        onClick = { showQuitDialog = true },
                        modifier = Modifier.testTag("exam_quit_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quit Exam",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // Countdown Timer Badge
                    Surface(
                        color = if (remainingSeconds <= 300) AccentPink else Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .testTag("exam_timer_badge")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = "Timer",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = formatDuration(remainingSeconds),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TealPrimary,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            // Submit Button
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = { showSubmitConfirmDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .height(48.dp)
                        .testTag("submit_exam_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "SUBMIT EXAMINATION (${userAnswers.size}/${questions.size})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        },
        containerColor = Color(0xFFF1F5F9)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("exam_questions_list"),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(questions) { index, q ->
                val selectedOption = userAnswers[q.id]
                val isAnswered = selectedOption != null
                val isExplanationExpanded = expandedExplanations[q.id] ?: false

                // Card matching Ethio Matric screenshot format
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("question_card_${index + 1}"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Header with question number and "Improve" badge
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${index + 1}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp,
                                color = Color(0xFF0F172A)
                            )

                            Text(
                                text = "Improve",
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8),
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Thin pink dividing line from screenshot
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(AccentPink)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Question diagram/figure if present
                        if (q.imageResId != null) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFF8FAFC),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = q.imageResId),
                                    contentDescription = q.imageDescription ?: "Exam Diagram",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 240.dp)
                                        .padding(6.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }

                        // Complete question text - Solid Bold & High Contrast
                        Text(
                            text = q.questionText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF0F172A),
                            lineHeight = 23.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Four Options
                        q.options.forEachIndexed { optIndex, optionText ->
                            val isSelected = selectedOption == optIndex
                            val isCorrectAnswer = q.correctAnswer == optIndex

                            // Determine highlight color based on revealMode
                            val showFeedback = revealMode == "right_away" && isAnswered

                            val optionBgColor = when {
                                showFeedback && isCorrectAnswer -> FeedbackCorrectGreen
                                showFeedback && isSelected && !isCorrectAnswer -> FeedbackIncorrectRed
                                isSelected -> TealPrimary.copy(alpha = 0.12f)
                                else -> Color.Transparent
                            }

                            val optionBorderColor = when {
                                showFeedback && isCorrectAnswer -> FeedbackCorrectBorder
                                showFeedback && isSelected && !isCorrectAnswer -> FeedbackIncorrectBorder
                                isSelected -> TealPrimary
                                else -> Color(0xFFCBD5E1)
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(optionBgColor)
                                    .border(1.5.dp, optionBorderColor, RoundedCornerShape(8.dp))
                                    .clickable(enabled = !(revealMode == "right_away" && isAnswered)) {
                                        userAnswers[q.id] = optIndex
                                    }
                                    .padding(horizontal = 12.dp, vertical = 11.dp)
                                    .testTag("q_${index + 1}_option_$optIndex"),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Radio selection icon
                                RadioButton(
                                    selected = isSelected,
                                    onClick = null,
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = if (showFeedback && !isCorrectAnswer) FeedbackIncorrectIcon else FeedbackCorrectIcon,
                                        unselectedColor = Color(0xFF64748B)
                                    ),
                                    modifier = Modifier.size(20.dp)
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                // Option Letter Label
                                val letterLabel = "${('A' + optIndex)}. "
                                Text(
                                    text = letterLabel,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = if (isSelected) TealPrimary else Color(0xFF334155)
                                )

                                // Option text - Solid Bold & High Contrast
                                Text(
                                    text = optionText,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF0F172A),
                                    modifier = Modifier.weight(1f)
                                )

                                // Trailing checkmark or cross in Right Away mode
                                if (showFeedback) {
                                    if (isCorrectAnswer) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Correct",
                                            tint = FeedbackCorrectIcon,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    } else if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Incorrect",
                                            tint = FeedbackIncorrectIcon,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Explanation Accordion Toggle (shown in Right Away mode after answered, or when submitted)
                        if ((revealMode == "right_away" && isAnswered) || isSubmitted) {
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier
                                    .clickable {
                                        expandedExplanations[q.id] = !isExplanationExpanded
                                    }
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isExplanationExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = TealPrimary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Explanation",
                                    color = TealPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }

                            AnimatedVisibility(visible = isExplanationExpanded) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFF8FAFC), RoundedCornerShape(8.dp))
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        text = "Topic: ${q.topic} (Grade ${q.grade}, Unit ${q.unitNumber})",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp,
                                        color = Color(0xFF475569)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = q.explanation,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF0F172A),
                                        lineHeight = 19.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Quit Dialog
    if (showQuitDialog) {
        QuitExamConfirmationDialog(
            onContinue = { showQuitDialog = false },
            onQuit = {
                showQuitDialog = false
                val session = ActiveExamSession(
                    subject = subjectTitle.lowercase(),
                    year = year,
                    isUnitPractice = isUnitPractice,
                    grade = grade,
                    unitTitle = unitTitle,
                    revealMode = revealMode,
                    targetEndTimeMillis = System.currentTimeMillis() + (remainingSeconds * 1000L),
                    totalDurationSeconds = totalDurationSeconds,
                    remainingSeconds = remainingSeconds,
                    answers = userAnswers.toMap(),
                    questionIds = questions.map { it.id }
                )
                onSaveAndQuit(session)
            }
        )
    }

    // Submit Confirmation Dialog
    if (showSubmitConfirmDialog) {
        val answeredCount = userAnswers.size
        val unansweredCount = questions.size - answeredCount

        AlertDialog(
            onDismissRequest = { showSubmitConfirmDialog = false },
            title = { Text("Submit Examination", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("You have answered $answeredCount of ${questions.size} questions.")
                    if (unansweredCount > 0) {
                        Text(
                            text = "Warning: $unansweredCount question(s) remain unanswered and will be marked as incorrect.",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSubmitConfirmDialog = false
                        isSubmitted = true
                        calculateAndSubmitExam(
                            questions = questions,
                            userAnswers = userAnswers,
                            subjectTitle = subjectTitle,
                            year = year,
                            isUnitPractice = isUnitPractice,
                            grade = grade,
                            unitTitle = unitTitle,
                            totalDurationSeconds = totalDurationSeconds,
                            timeRemainingSeconds = remainingSeconds,
                            studentProfile = studentProfile,
                            onSubmitExam = onSubmitExam
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                    modifier = Modifier.testTag("confirm_submit_button")
                ) {
                    Text("Confirm Submit")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showSubmitConfirmDialog = false }) {
                    Text("Review More")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    // Time Warnings Alert Dialog
    showWarningDialogText?.let { text ->
        AlertDialog(
            onDismissRequest = { showWarningDialogText = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = AccentPink)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Time Alert", fontWeight = FontWeight.Bold)
                }
            },
            text = { Text(text) },
            confirmButton = {
                Button(onClick = { showWarningDialogText = null }) {
                    Text("Understood")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

private fun calculateAndSubmitExam(
    questions: List<Question>,
    userAnswers: Map<String, Int>,
    subjectTitle: String,
    year: String,
    isUnitPractice: Boolean,
    grade: Int,
    unitTitle: String,
    totalDurationSeconds: Long,
    timeRemainingSeconds: Long,
    studentProfile: StudentProfile,
    onSubmitExam: (ExamAttempt) -> Unit
) {
    var correctCount = 0
    var incorrectCount = 0
    var unansweredCount = 0

    questions.forEach { q ->
        val chosen = userAnswers[q.id]
        if (chosen == null) {
            unansweredCount++
        } else if (chosen == q.correctAnswer) {
            correctCount++
        } else {
            incorrectCount++
        }
    }

    val total = questions.size
    val scorePercent = if (total > 0) (correctCount.toDouble() / total.toDouble()) * 100.0 else 0.0
    val timeTakenSeconds = (totalDurationSeconds - timeRemainingSeconds).coerceAtLeast(1L)

    val attempt = ExamAttempt(
        id = "att_" + UUID.randomUUID().toString().take(8),
        studentEmail = studentProfile.email,
        studentName = studentProfile.fullName,
        subject = subjectTitle.lowercase(),
        year = year,
        isUnitPractice = isUnitPractice,
        grade = grade,
        unitTitle = unitTitle,
        totalQuestions = total,
        correctCount = correctCount,
        incorrectCount = incorrectCount,
        unansweredCount = unansweredCount,
        scorePercentage = scorePercent,
        timeTakenSeconds = timeTakenSeconds,
        timeRemainingSeconds = timeRemainingSeconds,
        completedAt = System.currentTimeMillis(),
        userAnswers = userAnswers
    )

    onSubmitExam(attempt)
}

private fun formatDuration(seconds: Long): String {
    val hrs = seconds / 3600
    val mins = (seconds % 3600) / 60
    val secs = seconds % 60
    return "%02d:%02d:%02d".format(hrs, mins, secs)
}
