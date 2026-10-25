package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.ExamDataManager
import com.example.data.QuestionRepository
import com.example.model.ExamAttempt
import com.example.model.StudentProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `verify app name resource`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("3RM Entrance Exam", appName)
    }

    @Test
    fun `verify question bank loaded and categorized`() {
        val repo = QuestionRepository()
        val allQuestions = repo.getAllQuestions()
        assertTrue("Question bank should not be empty", allQuestions.isNotEmpty())

        val chemQuestions = repo.getQuestionsBySubject("chemistry")
        assertTrue("Chemistry questions should be populated", chemQuestions.isNotEmpty())
        assertTrue("Chemistry questions should be categorized with grades", chemQuestions.all { it.grade in 9..12 })

        val bioQuestions = repo.getQuestionsBySubject("biology")
        assertTrue("Biology questions should be populated", bioQuestions.isNotEmpty())

        val engQuestions = repo.getQuestionsBySubject("english")
        assertTrue("English questions should be populated", engQuestions.isNotEmpty())
    }

    @Test
    fun `verify exam data manager profile and attempts persistence`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val dataManager = ExamDataManager(context)

        val testProfile = StudentProfile(
            id = "test_std_1",
            fullName = "Abebe Bikila",
            phone = "+251911000000",
            email = "abebe.test@gmail.com"
        )
        dataManager.saveProfile(testProfile)

        val retrieved = dataManager.getSavedProfile()
        assertNotNull(retrieved)
        assertEquals("Abebe Bikila", retrieved?.fullName)
        assertEquals("abebe.test@gmail.com", retrieved?.email)

        // Test attempt record
        val attempt = ExamAttempt(
            id = "test_att_1",
            studentEmail = "abebe.test@gmail.com",
            studentName = "Abebe Bikila",
            subject = "chemistry",
            year = "2017",
            isUnitPractice = false,
            totalQuestions = 80,
            correctCount = 60,
            incorrectCount = 20,
            unansweredCount = 0,
            scorePercentage = 75.0,
            timeTakenSeconds = 3600,
            timeRemainingSeconds = 5400,
            completedAt = System.currentTimeMillis()
        )
        dataManager.saveExamAttempt(attempt)

        val attempts = dataManager.getAllAttempts()
        assertTrue(attempts.any { it.id == "test_att_1" })
    }
}
