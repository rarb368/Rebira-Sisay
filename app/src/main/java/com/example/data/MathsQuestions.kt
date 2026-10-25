package com.example.data

import com.example.model.Question

object MathsQuestions {
    fun getNaturalMathsQuestions(): List<Question> = listOf(
        Question(
            id = "math_nat_2017_1",
            subject = "maths_nat",
            grade = 11,
            unitNumber = 1,
            unitTitle = "Relations and Functions",
            topic = "Domain and Range",
            year = "2017",
            questionNumber = 1,
            questionText = "Which one of the following is the domain of f(x) = sqrt(x² - 4) / (x - 3)?",
            options = listOf(
                "(-∞, -2] ∪ [2, ∞) \\ {3}",
                "[-2, 2] \\ {3}",
                "(-∞, -2) ∪ (2, ∞)",
                "[2, ∞) \\ {3}"
            ),
            correctAnswer = 0,
            explanation = "For f(x) to be defined in real numbers, x² - 4 >= 0, so x <= -2 or x >= 2. Additionally, denominator x - 3 != 0, so x != 3. Hence (-∞, -2] ∪ [2, ∞) \\ {3}."
        ),
        Question(
            id = "math_nat_2017_2",
            subject = "maths_nat",
            grade = 12,
            unitNumber = 2,
            unitTitle = "Introduction to Calculus",
            topic = "Limits and Derivatives",
            year = "2017",
            questionNumber = 2,
            questionText = "What is the value of limit as x approaches 0 of (sin(5x) / x)?",
            options = listOf("0", "1", "5", "Does not exist"),
            correctAnswer = 2,
            explanation = "Using the standard limit identity lim(t->0) sin(t)/t = 1, we rewrite (sin(5x) / x) as 5 * (sin(5x) / 5x) -> 5 * 1 = 5."
        ),
        Question(
            id = "math_nat_2017_3",
            subject = "maths_nat",
            grade = 12,
            unitNumber = 3,
            unitTitle = "Integration",
            topic = "Definite Integrals",
            year = "2017",
            questionNumber = 3,
            questionText = "Evaluate the integral from 0 to 2 of (3x² - 2x + 1) dx.",
            options = listOf("6", "8", "4", "10"),
            correctAnswer = 0,
            explanation = "Antiderivative F(x) = x³ - x² + x. Evaluated from 0 to 2: F(2) = (2)³ - (2)² + 2 = 8 - 4 + 2 = 6. F(0) = 0. Therefore, the integral equals 6."
        ),
        Question(
            id = "math_nat_2017_4",
            subject = "maths_nat",
            grade = 11,
            unitNumber = 4,
            unitTitle = "Coordinate Geometry",
            topic = "Conic Sections: Circles",
            year = "2017",
            questionNumber = 4,
            questionText = "What is the center and radius of the circle given by x² + y² - 6x + 8y = 0?",
            options = listOf(
                "Center (3, -4), Radius 5",
                "Center (-3, 4), Radius 5",
                "Center (3, -4), Radius 25",
                "Center (-3, -4), Radius 5"
            ),
            correctAnswer = 0,
            explanation = "Completing the squares: (x - 3)² - 9 + (y + 4)² - 16 = 0 => (x - 3)² + (y + 4)² = 25. Thus center is (3, -4) and radius r = sqrt(25) = 5."
        )
    )

    fun getSocialMathsQuestions(): List<Question> = listOf(
        Question(
            id = "math_soc_2017_1",
            subject = "maths_soc",
            grade = 11,
            unitNumber = 2,
            unitTitle = "Matrices and Determinants",
            topic = "Determinant of 2x2 Matrix",
            year = "2017",
            questionNumber = 1,
            questionText = "What is the determinant of the matrix A = [[4, 2], [3, 5]]?",
            options = listOf("14", "26", "20", "6"),
            correctAnswer = 0,
            explanation = "For a 2x2 matrix [[a, b], [c, d]], the determinant det(A) = ad - bc = (4 * 5) - (2 * 3) = 20 - 6 = 14."
        ),
        Question(
            id = "math_soc_2017_2",
            subject = "maths_soc",
            grade = 12,
            unitNumber = 1,
            unitTitle = "Sequences and Series",
            topic = "Arithmetic Progressions",
            year = "2017",
            questionNumber = 2,
            questionText = "In an arithmetic progression, the first term is 5 and the common difference is 3. What is the sum of the first 10 terms?",
            options = listOf("185", "190", "175", "200"),
            correctAnswer = 0,
            explanation = "S_n = (n / 2) * [2a + (n - 1)d] = (10 / 2) * [2(5) + 9(3)] = 5 * [10 + 27] = 5 * 37 = 185."
        ),
        Question(
            id = "math_soc_2017_3",
            subject = "maths_soc",
            grade = 10,
            unitNumber = 3,
            unitTitle = "Statistics and Probability",
            topic = "Probability of Independent Events",
            year = "2017",
            questionNumber = 3,
            questionText = "A fair six-sided die is tossed twice. What is the probability of getting a sum equal to 7?",
            options = listOf("1/6", "1/12", "5/36", "7/36"),
            correctAnswer = 0,
            explanation = "Total outcomes = 36. Outcomes with sum 7 are: (1,6), (2,5), (3,4), (4,3), (5,2), (6,1) -> 6 outcomes. Probability = 6 / 36 = 1/6."
        )
    )
}
