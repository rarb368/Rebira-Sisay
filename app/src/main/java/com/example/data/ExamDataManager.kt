package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.ActiveExamSession
import com.example.model.ExamAttempt
import com.example.model.StudentProfile
import org.json.JSONArray
import org.json.JSONObject

class ExamDataManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("3rm_exam_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_PROFILE_ID = "profile_id"
        private const val KEY_PROFILE_NAME = "profile_name"
        private const val KEY_PROFILE_PHONE = "profile_phone"
        private const val KEY_PROFILE_EMAIL = "profile_email"
        private const val KEY_PROFILE_REGISTERED = "profile_registered_at"
        private const val KEY_STUDENTS_LIST = "all_students_json"
        private const val KEY_ATTEMPTS_LIST = "all_attempts_json"
        private const val KEY_ACTIVE_SESSION = "active_session_json"
    }

    // Profile Management
    fun getSavedProfile(): StudentProfile? {
        val email = prefs.getString(KEY_PROFILE_EMAIL, null) ?: return null
        return StudentProfile(
            id = prefs.getString(KEY_PROFILE_ID, "") ?: "",
            fullName = prefs.getString(KEY_PROFILE_NAME, "") ?: "",
            phone = prefs.getString(KEY_PROFILE_PHONE, "") ?: "",
            email = email,
            registeredAt = prefs.getLong(KEY_PROFILE_REGISTERED, System.currentTimeMillis())
        )
    }

    fun saveProfile(profile: StudentProfile) {
        prefs.edit()
            .putString(KEY_PROFILE_ID, profile.id)
            .putString(KEY_PROFILE_NAME, profile.fullName)
            .putString(KEY_PROFILE_PHONE, profile.phone)
            .putString(KEY_PROFILE_EMAIL, profile.email)
            .putLong(KEY_PROFILE_REGISTERED, profile.registeredAt)
            .apply()

        // Also add to central registered students list
        val students = getAllRegisteredStudents().toMutableList()
        val existingIndex = students.indexOfFirst { it.email.equals(profile.email, ignoreCase = true) }
        if (existingIndex != -1) {
            students[existingIndex] = profile
        } else {
            students.add(profile)
        }
        saveAllStudents(students)
    }

    fun getAllRegisteredStudents(): List<StudentProfile> {
        val jsonStr = prefs.getString(KEY_STUDENTS_LIST, null) ?: return defaultSampleStudents()
        return try {
            val array = JSONArray(jsonStr)
            val list = mutableListOf<StudentProfile>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    StudentProfile(
                        id = obj.optString("id"),
                        fullName = obj.optString("fullName"),
                        phone = obj.optString("phone"),
                        email = obj.optString("email"),
                        registeredAt = obj.optLong("registeredAt", System.currentTimeMillis())
                    )
                )
            }
            if (list.isEmpty()) defaultSampleStudents() else list
        } catch (_: Exception) {
            defaultSampleStudents()
        }
    }

    private fun saveAllStudents(students: List<StudentProfile>) {
        val array = JSONArray()
        for (s in students) {
            val obj = JSONObject().apply {
                put("id", s.id)
                put("fullName", s.fullName)
                put("phone", s.phone)
                put("email", s.email)
                put("registeredAt", s.registeredAt)
            }
            array.put(obj)
        }
        prefs.edit().putString(KEY_STUDENTS_LIST, array.toString()).apply()
    }

    private fun defaultSampleStudents(): List<StudentProfile> {
        val current = getSavedProfile()
        val list = mutableListOf<StudentProfile>()
        if (current != null) list.add(current)
        list.add(
            StudentProfile(
                id = "std_aibs_01",
                fullName = "Chala Gemechu",
                phone = "+251911223344",
                email = "chala.gemechu@gmail.com",
                registeredAt = System.currentTimeMillis() - 86400000L * 3
            )
        )
        list.add(
            StudentProfile(
                id = "std_aibs_02",
                fullName = "Hawwi Tolessa",
                phone = "+251922334455",
                email = "hawwi.tolessa@gmail.com",
                registeredAt = System.currentTimeMillis() - 86400000L * 5
            )
        )
        return list
    }

    // Attempts and Exam History
    fun saveExamAttempt(attempt: ExamAttempt) {
        val attempts = getAllAttempts().toMutableList()
        attempts.add(0, attempt)
        val array = JSONArray()
        for (a in attempts) {
            val obj = JSONObject().apply {
                put("id", a.id)
                put("studentEmail", a.studentEmail)
                put("studentName", a.studentName)
                put("subject", a.subject)
                put("year", a.year)
                put("isUnitPractice", a.isUnitPractice)
                put("grade", a.grade)
                put("unitTitle", a.unitTitle)
                put("totalQuestions", a.totalQuestions)
                put("correctCount", a.correctCount)
                put("incorrectCount", a.incorrectCount)
                put("unansweredCount", a.unansweredCount)
                put("scorePercentage", a.scorePercentage)
                put("timeTakenSeconds", a.timeTakenSeconds)
                put("timeRemainingSeconds", a.timeRemainingSeconds)
                put("completedAt", a.completedAt)
            }
            array.put(obj)
        }
        prefs.edit().putString(KEY_ATTEMPTS_LIST, array.toString()).apply()

        // Clear active session if matching
        clearActiveSession()
    }

    fun getAllAttempts(): List<ExamAttempt> {
        val jsonStr = prefs.getString(KEY_ATTEMPTS_LIST, null) ?: return defaultSampleAttempts()
        return try {
            val array = JSONArray(jsonStr)
            val list = mutableListOf<ExamAttempt>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    ExamAttempt(
                        id = obj.optString("id"),
                        studentEmail = obj.optString("studentEmail"),
                        studentName = obj.optString("studentName"),
                        subject = obj.optString("subject"),
                        year = obj.optString("year"),
                        isUnitPractice = obj.optBoolean("isUnitPractice", false),
                        grade = obj.optInt("grade", 12),
                        unitTitle = obj.optString("unitTitle"),
                        totalQuestions = obj.optInt("totalQuestions"),
                        correctCount = obj.optInt("correctCount"),
                        incorrectCount = obj.optInt("incorrectCount"),
                        unansweredCount = obj.optInt("unansweredCount"),
                        scorePercentage = obj.optDouble("scorePercentage"),
                        timeTakenSeconds = obj.optLong("timeTakenSeconds"),
                        timeRemainingSeconds = obj.optLong("timeRemainingSeconds"),
                        completedAt = obj.optLong("completedAt")
                    )
                )
            }
            if (list.isEmpty()) defaultSampleAttempts() else list
        } catch (_: Exception) {
            defaultSampleAttempts()
        }
    }

    private fun defaultSampleAttempts(): List<ExamAttempt> {
        return listOf(
            ExamAttempt(
                id = "att_01",
                studentEmail = "chala.gemechu@gmail.com",
                studentName = "Chala Gemechu",
                subject = "chemistry",
                year = "2017",
                isUnitPractice = false,
                totalQuestions = 80,
                correctCount = 64,
                incorrectCount = 14,
                unansweredCount = 2,
                scorePercentage = 80.0,
                timeTakenSeconds = 7200,
                timeRemainingSeconds = 1800,
                completedAt = System.currentTimeMillis() - 86400000L
            ),
            ExamAttempt(
                id = "att_02",
                studentEmail = "hawwi.tolessa@gmail.com",
                studentName = "Hawwi Tolessa",
                subject = "biology",
                year = "2017",
                isUnitPractice = false,
                totalQuestions = 100,
                correctCount = 85,
                incorrectCount = 15,
                unansweredCount = 0,
                scorePercentage = 85.0,
                timeTakenSeconds = 8000,
                timeRemainingSeconds = 1000,
                completedAt = System.currentTimeMillis() - 86400000L * 2
            )
        )
    }

    fun getBestScoreForSubject(subject: String): Double? {
        val matches = getAllAttempts().filter { it.subject.equals(subject, ignoreCase = true) }
        return matches.maxOfOrNull { it.scorePercentage }
    }

    fun getLastAttemptForSubject(subject: String): ExamAttempt? {
        return getAllAttempts().firstOrNull { it.subject.equals(subject, ignoreCase = true) }
    }

    fun getUnitParticipation(subject: String, grade: Int, unitTitle: String): Float {
        val attempts = getAllAttempts().filter {
            it.subject.equals(subject, ignoreCase = true) &&
                    it.isUnitPractice &&
                    it.grade == grade &&
                    it.unitTitle.equals(unitTitle, ignoreCase = true)
        }
        if (attempts.isEmpty()) return 0f
        val latest = attempts.first()
        return (latest.correctCount.toFloat() / latest.totalQuestions.coerceAtLeast(1)).coerceIn(0f, 1f)
    }

    // Active Exam Session (Quit & Resume support)
    fun saveActiveSession(session: ActiveExamSession) {
        val obj = JSONObject().apply {
            put("subject", session.subject)
            put("year", session.year)
            put("isUnitPractice", session.isUnitPractice)
            put("grade", session.grade)
            put("unitTitle", session.unitTitle)
            put("revealMode", session.revealMode)
            put("targetEndTimeMillis", session.targetEndTimeMillis)
            put("totalDurationSeconds", session.totalDurationSeconds)
            put("remainingSeconds", session.remainingSeconds)

            val answersObj = JSONObject()
            session.answers.forEach { (k, v) -> answersObj.put(k, v) }
            put("answers", answersObj)

            val qArray = JSONArray()
            session.questionIds.forEach { qArray.put(it) }
            put("questionIds", qArray)
        }
        prefs.edit().putString(KEY_ACTIVE_SESSION, obj.toString()).apply()
    }

    fun getActiveSession(): ActiveExamSession? {
        val jsonStr = prefs.getString(KEY_ACTIVE_SESSION, null) ?: return null
        return try {
            val obj = JSONObject(jsonStr)
            val answersMap = mutableMapOf<String, Int>()
            val answersObj = obj.optJSONObject("answers")
            answersObj?.keys()?.forEach { key ->
                answersMap[key] = answersObj.getInt(key)
            }
            val qList = mutableListOf<String>()
            val qArray = obj.optJSONArray("questionIds")
            if (qArray != null) {
                for (i in 0 until qArray.length()) {
                    qList.add(qArray.getString(i))
                }
            }

            ActiveExamSession(
                subject = obj.optString("subject"),
                year = obj.optString("year"),
                isUnitPractice = obj.optBoolean("isUnitPractice"),
                grade = obj.optInt("grade"),
                unitTitle = obj.optString("unitTitle"),
                revealMode = obj.optString("revealMode", "right_away"),
                targetEndTimeMillis = obj.optLong("targetEndTimeMillis"),
                totalDurationSeconds = obj.optLong("totalDurationSeconds"),
                remainingSeconds = obj.optLong("remainingSeconds"),
                answers = answersMap,
                questionIds = qList
            )
        } catch (_: Exception) {
            null
        }
    }

    fun clearActiveSession() {
        prefs.edit().remove(KEY_ACTIVE_SESSION).apply()
    }

    // Export CSV for Admin
    fun generateStudentsCsv(): String {
        val students = getAllRegisteredStudents()
        val attempts = getAllAttempts()

        val sb = StringBuilder()
        sb.append("ID,Full Name,Phone Number,Gmail,Registration Date,Total Exams Taken,Average Score (%)\n")
        for (s in students) {
            val sAttempts = attempts.filter { it.studentEmail.equals(s.email, ignoreCase = true) }
            val avgScore = if (sAttempts.isNotEmpty()) {
                "%.1f".format(sAttempts.map { it.scorePercentage }.average())
            } else {
                "0.0"
            }
            sb.append("\"${s.id}\",")
            sb.append("\"${s.fullName}\",")
            sb.append("\"${s.phone}\",")
            sb.append("\"${s.email}\",")
            sb.append("\"${java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(java.util.Date(s.registeredAt))}\",")
            sb.append("${sAttempts.size},")
            sb.append("$avgScore\n")
        }
        return sb.toString()
    }
}
