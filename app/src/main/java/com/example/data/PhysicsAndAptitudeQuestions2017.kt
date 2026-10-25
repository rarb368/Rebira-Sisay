package com.example.data

import com.example.model.Question

object PhysicsAndAptitudeQuestions2017 {
    fun getPhysicsQuestions(): List<Question> = listOf(
        Question(
            id = "phys_2017_1",
            subject = "physics",
            grade = 9,
            unitNumber = 1,
            unitTitle = "Physics and Human Society",
            topic = "Vectors and Scalars",
            year = "2017",
            questionNumber = 1,
            questionText = "Vector quantities are represented by",
            options = listOf("magnitude and direction.", "direction only.", "magnitude only.", "dimensionless values."),
            correctAnswer = 0,
            explanation = "A vector quantity possesses both magnitude (numerical size with unit) and spatial direction (e.g., velocity, acceleration, force)."
        ),
        Question(
            id = "phys_2017_2",
            subject = "physics",
            grade = 9,
            unitNumber = 3,
            unitTitle = "Forces and Newton's Laws",
            topic = "Newton's First Law",
            year = "2017",
            questionNumber = 2,
            questionText = "According to Newton's first law, an object that is in motion continues its state of motion with",
            options = listOf(
                "a decreasing speed if the resultant force exerted on it is zero.",
                "constant velocity if the resultant force exerted on it is not zero.",
                "an increasing speed if the resultant force exerted on it is not zero.",
                "constant velocity if the resultant force exerted on it is zero."
            ),
            correctAnswer = 3,
            explanation = "Newton's first law (law of inertia) dictates that when the net external force on an object is zero, it preserves its constant velocity in a straight line."
        ),
        Question(
            id = "phys_2017_3",
            subject = "physics",
            grade = 9,
            unitNumber = 2,
            unitTitle = "Motion in One Dimension",
            topic = "Distance and Displacement",
            year = "2017",
            questionNumber = 3,
            questionText = "Which of the following correctly describes the difference between distance and displacement?",
            options = listOf(
                "Distance is described in magnitude and direction, while displacement is magnitude only.",
                "Distance depends on initial and final points, while displacement depends on path.",
                "Distance is always greater than or equal to displacement magnitude, which is the shortest path.",
                "Distance has positive or negative values, while displacement is positive only."
            ),
            correctAnswer = 2,
            explanation = "Distance is the scalar total length of the actual path taken. Displacement is the vector straight-line path from start to end; therefore distance >= |displacement|."
        ),
        Question(
            id = "phys_2017_4",
            subject = "physics",
            grade = 9,
            unitNumber = 2,
            unitTitle = "Motion in One Dimension",
            topic = "Uniformly Accelerated Motion",
            year = "2017",
            questionNumber = 4,
            questionText = "Which of the following statements is correct about a uniformly accelerated motion?",
            options = listOf("The velocity is constant.", "The acceleration is constant.", "The speed is constant but direction changes.", "The displacement increases at a uniform rate."),
            correctAnswer = 1,
            explanation = "In uniformly accelerated motion, the rate of change of velocity (acceleration) remains constant over time."
        ),
        Question(
            id = "phys_2017_5",
            subject = "physics",
            grade = 11,
            unitNumber = 2,
            unitTitle = "Two-Dimensional Motion",
            topic = "Vector Addition",
            year = "2017",
            questionNumber = 5,
            questionText = "Two displacement vectors have magnitudes 4 m and 3 m. Which one of the following is NOT the possible value of the magnitude of their resultant vector?",
            options = listOf("1 m", "7 m", "5 m", "12 m"),
            correctAnswer = 3,
            explanation = "The resultant magnitude of two vectors of 4 m and 3 m must lie in the range [|4 - 3|, 4 + 3] = [1 m, 7 m]. 12 m is mathematically impossible."
        ),
        Question(
            id = "phys_2017_11",
            subject = "physics",
            grade = 11,
            unitNumber = 3,
            unitTitle = "Law of Conservation of Energy and Momentum",
            topic = "Newton's Second Law",
            year = "2017",
            questionNumber = 11,
            questionText = "A 2 kg object accelerates with 1.5 m/s² to the right on a horizontal frictionless surface under two forces: F2 = 1 N (left) and F1 (right). What is F1?",
            options = listOf("2 N", "1 N", "3 N", "4 N"),
            correctAnswer = 3,
            explanation = "Net force F_net = m * a = 2 kg * 1.5 m/s² = 3 N to the right. Since F1 - F2 = 3 N and F2 = 1 N, F1 = 3 + 1 = 4 N."
        ),
        Question(
            id = "phys_2017_17",
            subject = "physics",
            grade = 10,
            unitNumber = 2,
            unitTitle = "Simple Machines",
            topic = "Wheel and Axle Efficiency",
            year = "2017",
            questionNumber = 17,
            questionText = "A wheel and axle of radii 40 cm and 8 cm lifts a bucket of 6 kg water by applying an effort of 20 N. What is the efficiency?",
            options = listOf("60 %", "66.7 %", "30 %", "80 %"),
            correctAnswer = 0,
            explanation = "Ideal Mechanical Advantage IMA = R / r = 40 / 8 = 5. Load = m * g = 6 * 10 = 60 N. Actual Mechanical Advantage AMA = Load / Effort = 60 / 20 = 3. Efficiency = AMA / IMA * 100% = (3 / 5) * 100% = 60%."
        ),
        Question(
            id = "phys_2017_55",
            subject = "physics",
            grade = 10,
            unitNumber = 4,
            unitTitle = "Electromagnetism",
            topic = "Magnetic Field of Current",
            year = "2017",
            questionNumber = 55,
            questionText = "The magnetic field created by a long straight current carrying wire",
            options = listOf(
                "is directly proportional to the distance from the wire.",
                "is directed in the same direction as the current.",
                "is inversely proportional to the current in the wire.",
                "forms circular pattern around the wire."
            ),
            correctAnswer = 3,
            explanation = "According to the right-hand grip rule, magnetic field lines around a long straight current-carrying wire form concentric circles centered on the wire."
        ),
        Question(
            id = "phys_2017_56",
            subject = "physics",
            grade = 12,
            unitNumber = 4,
            unitTitle = "Electronics",
            topic = "Semiconductor Doping",
            year = "2017",
            questionNumber = 56,
            questionText = "What will happen on a pure semiconductor when it is doped by group III element?",
            options = listOf("It becomes N-type semiconductor.", "It becomes P-type semiconductor.", "It becomes intrinsic semiconductor.", "It gets extra free electrons."),
            correctAnswer = 1,
            explanation = "Group III elements (trivalent, like Boron or Indium) introduce positive holes into the silicon crystal lattice, creating a P-type semiconductor."
        ),
        Question(
            id = "phys_2017_60",
            subject = "physics",
            grade = 12,
            unitNumber = 3,
            unitTitle = "Electromagnetic Induction",
            topic = "Faraday's Law of Induction",
            year = "2017",
            questionNumber = 60,
            questionText = "A circular coil is located in a magnetic field B = 0.50 T. The area increases by 4.0 × 10^-3 m² in 2 s. What is the induced EMF?",
            options = listOf("1.0 × 10^-3 V", "4.0 × 10^-3 V", "1.0 × 10^3 V", "2.5 × 10^2 V"),
            correctAnswer = 0,
            explanation = "Induced emf = |Delta Phi / Delta t| = B * (Delta A / Delta t) = 0.50 T * (4.0 x 10^-3 m² / 2 s) = 1.0 x 10^-3 V."
        )
    )

    fun getAptitudeQuestions(): List<Question> = listOf(
        Question(
            id = "apt_2017_1",
            subject = "sat",
            grade = 12,
            unitNumber = 1,
            unitTitle = "Analytical Reasoning",
            topic = "Calendar and Time Logic",
            year = "2017",
            questionNumber = 1,
            questionText = "If two days after tomorrow is four days before Saturday, what day is it today?",
            options = listOf("Friday", "Saturday", "Sunday", "Monday"),
            correctAnswer = 3,
            explanation = "Four days before Saturday is Tuesday. If two days after tomorrow is Tuesday, then tomorrow is Sunday, meaning today is Monday."
        ),
        Question(
            id = "apt_2017_2",
            subject = "sat",
            grade = 12,
            unitNumber = 1,
            unitTitle = "Logical Reasoning",
            topic = "Relative Order",
            year = "2017",
            questionNumber = 2,
            questionText = "Gad is older than Dan and Dan is older than Betti. With Senni being older than Dan and younger than Gad, who is the youngest?",
            options = listOf("Betti", "Senni", "Gad", "Dan"),
            correctAnswer = 0,
            explanation = "Ordering from oldest to youngest: Gad > Senni > Dan > Betti. Therefore, Betti is the youngest."
        ),
        Question(
            id = "apt_2017_4",
            subject = "sat",
            grade = 12,
            unitNumber = 1,
            unitTitle = "Logical Reasoning",
            topic = "Syllogisms",
            year = "2017",
            questionNumber = 4,
            questionText = "Statement I: All football players are sportspersons. Statement II: All sportspersons are fit. What conclusion follows?",
            options = listOf(
                "Some football players are fit.",
                "incorrect",
                "reasonably correct",
                "appropriate"
            ),
            correctAnswer = 2,
            explanation = "Since all football players are sportspersons and all sportspersons are fit, all football players are fit, making 'Some football players are fit' logically and reasonably sound."
        ),
        Question(
            id = "apt_2017_19",
            subject = "sat",
            grade = 12,
            unitNumber = 2,
            unitTitle = "Verbal Reasoning",
            topic = "Analogy",
            year = "2017",
            questionNumber = 19,
            questionText = "AUTOMOBILE: GARAGE :: Hangar : ?",
            options = listOf("Deck", "Truck", "Train", "Airplane"),
            correctAnswer = 3,
            explanation = "An automobile is housed and stored in a garage; similarly, an airplane is housed in a hangar."
        ),
        Question(
            id = "apt_2017_41",
            subject = "sat",
            grade = 12,
            unitNumber = 3,
            unitTitle = "Quantitative Reasoning",
            topic = "Work and Rate Problems",
            year = "2017",
            questionNumber = 41,
            questionText = "Pipe A can fill a water tank in 3 hours, and pipe B can fill it in 6 hours. How long will it take in hours if both pipes are open together?",
            options = listOf("5", "4", "9", "2"),
            correctAnswer = 3,
            explanation = "Combined rate = 1/3 + 1/6 = 2/6 + 1/6 = 3/6 = 1/2 tank per hour. Time required = 1 / (1/2) = 2 hours."
        ),
        Question(
            id = "apt_2017_43",
            subject = "sat",
            grade = 12,
            unitNumber = 3,
            unitTitle = "Quantitative Reasoning",
            topic = "Geometry: Solids",
            year = "2017",
            questionNumber = 43,
            questionText = "If a spherical soccer ball has a volume of 36 π cm³, what is the surface area of the ball in cm²?",
            options = listOf("(16π)/3", "12 π", "36 π", "(32π)/3"),
            correctAnswer = 2,
            explanation = "Volume V = (4/3) * pi * r³ = 36 pi => r³ = 27 => r = 3 cm. Surface area = 4 * pi * r² = 4 * pi * 3² = 36 pi cm²."
        ),
        Question(
            id = "apt_2017_49",
            subject = "sat",
            grade = 12,
            unitNumber = 3,
            unitTitle = "Quantitative Reasoning",
            topic = "Algebra and Exponents",
            year = "2017",
            questionNumber = 49,
            questionText = "If -x + 2y = 4, then what is the value of (25^y) / (5^x)?",
            options = listOf("125", "625", "5", "25"),
            correctAnswer = 1,
            explanation = "25^y = (5²)^y = 5^(2y). Then (5^(2y)) / (5^x) = 5^(2y - x). Since 2y - x = 4, 5^4 = 625."
        ),
        Question(
            id = "apt_2017_56",
            subject = "sat",
            grade = 12,
            unitNumber = 3,
            unitTitle = "Quantitative Reasoning",
            topic = "Number Sequences",
            year = "2017",
            questionNumber = 56,
            questionText = "What is the value of z in the sequence 1, 2, 4, 8, 10, 20, 22, z, ...?",
            options = listOf("44", "24", "32", "40"),
            correctAnswer = 0,
            explanation = "The pattern alternates between multiplying by 2 and adding 2: 1*2=2, 2+2=4, 4*2=8, 8+2=10, 10*2=20, 20+2=22, 22*2=44. Thus z = 44."
        )
    )
}
