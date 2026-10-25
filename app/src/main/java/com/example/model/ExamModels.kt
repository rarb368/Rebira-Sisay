package com.example.model

enum class ExamSubject(
    val id: String,
    val title: String,
    val totalDefaultQuestions: Int,
    val defaultDurationMinutes: Int,
    val hexColor: Long
) {
    BIOLOGY("biology", "BIOLOGY", 100, 150, 0xFF43A047),
    CHEMISTRY("chemistry", "CHEMISTRY", 80, 150, 0xFF00ACC1),
    PHYSICS("physics", "PHYSICS", 60, 150, 0xFFE91E63),
    MATHS_NAT("maths_nat", "MATHS NAT", 60, 180, 0xFF546E7A),
    MATHS_SOC("maths_soc", "MATHS SOC", 60, 180, 0xFF3949AB),
    ENGLISH("english", "ENGLISH", 100, 120, 0xFF26A69A),
    SAT("sat", "SAT", 60, 120, 0xFFD81B60);

    companion object {
        fun fromId(id: String): ExamSubject = entries.firstOrNull { it.id == id } ?: BIOLOGY
    }
}

data class StudentProfile(
    val id: String = "",
    val fullName: String = "",
    val phone: String = "",
    val email: String = "",
    val registeredAt: Long = System.currentTimeMillis()
)

data class Question(
    val id: String,
    val subject: String,
    val grade: Int,
    val unitNumber: Int,
    val unitTitle: String,
    val topic: String,
    val year: String,
    val questionNumber: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: Int, // 0 = A, 1 = B, 2 = C, 3 = D
    val explanation: String,
    val isCustom: Boolean = false,
    val imageResId: Int? = null,
    val imageDescription: String? = null
)

data class ExamAttempt(
    val id: String,
    val studentEmail: String,
    val studentName: String,
    val subject: String,
    val year: String,
    val isUnitPractice: Boolean,
    val grade: Int = 12,
    val unitTitle: String = "",
    val totalQuestions: Int,
    val correctCount: Int,
    val incorrectCount: Int,
    val unansweredCount: Int,
    val scorePercentage: Double,
    val timeTakenSeconds: Long,
    val timeRemainingSeconds: Long,
    val completedAt: Long,
    val userAnswers: Map<String, Int> = emptyMap()
)

data class ActiveExamSession(
    val subject: String,
    val year: String,
    val isUnitPractice: Boolean,
    val grade: Int,
    val unitTitle: String,
    val revealMode: String, // "right_away" or "after_finishing"
    val targetEndTimeMillis: Long,
    val totalDurationSeconds: Long,
    val remainingSeconds: Long,
    val answers: Map<String, Int>,
    val questionIds: List<String>
)

data class UnitSummary(
    val grade: Int,
    val unitNumber: Int,
    val unitTitle: String,
    val totalQuestions: Int,
    val attemptedQuestions: Int,
    val correctQuestions: Int
) {
    val participationLevel: Float
        get() = if (totalQuestions > 0) attemptedQuestions.toFloat() / totalQuestions else 0f
}
