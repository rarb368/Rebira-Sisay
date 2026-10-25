package com.example.data

import com.example.R
import com.example.model.Question

object DiagramQuestions2017 {

    fun getDiagramQuestions(): List<Question> = listOf(
        // Chemistry Galvanic Cell Diagram Question
        Question(
            id = "chem_2017_66_img",
            subject = "chemistry",
            grade = 12,
            unitNumber = 3,
            unitTitle = "Electrochemistry",
            topic = "Galvanic Cells and Half-Reactions",
            year = "2017",
            questionNumber = 66,
            questionText = "Based on the galvanic cell diagram shown above, which half-reaction takes place at the copper cathode, and in which direction do electrons flow in the external circuit?",
            options = listOf(
                "Cu²⁺(aq) + 2e⁻ → Cu(s); electrons flow from Zn (anode) to Cu (cathode)",
                "Zn(s) → Zn²⁺(aq) + 2e⁻; electrons flow from Cu (cathode) to Zn (anode)",
                "Cu(s) → Cu²⁺(aq) + 2e⁻; electrons flow from Zn (anode) to Cu (cathode)",
                "Zn²⁺(aq) + 2e⁻ → Zn(s); electrons flow through the salt bridge"
            ),
            correctAnswer = 0,
            explanation = "In the Daniell/galvanic cell, zinc has a more negative standard reduction potential and undergoes oxidation at the anode (Zn → Zn²⁺ + 2e⁻). Electrons travel through the external circuit toward the copper cathode, where Cu²⁺ ions undergo reduction to solid Cu (Cu²⁺ + 2e⁻ → Cu). The salt bridge maintains electrical neutrality by ion migration.",
            imageResId = R.drawable.img_galvanic_cell,
            imageDescription = "Schematic diagram of an electrochemical galvanic cell with zinc anode and copper cathode"
        ),

        // Biology Human Heart Diagram Question
        Question(
            id = "bio_2017_10_img",
            subject = "biology",
            grade = 10,
            unitNumber = 2,
            unitTitle = "Human Biology and Health",
            topic = "Circulatory System: Heart Structure",
            year = "2017",
            questionNumber = 10,
            questionText = "Refer to the diagram of the human heart above. Which chamber generates the highest pressure to pump oxygenated blood out through the aorta into systemic circulation?",
            options = listOf(
                "Right Atrium (RA)",
                "Right Ventricle (RV)",
                "Left Ventricle (LV)",
                "Left Atrium (LA)"
            ),
            correctAnswer = 2,
            explanation = "The Left Ventricle (LV) possesses the thickest muscular myocardium because it must generate high hydrostatic pressure to pump oxygenated blood through the aortic semilunar valve and aorta to the entire systemic circulation.",
            imageResId = R.drawable.img_human_heart,
            imageDescription = "Internal anatomical diagram of the human heart showing all four chambers and major blood vessels"
        ),

        // Physics Inclined Plane Mechanics Question
        Question(
            id = "phys_2017_16_img",
            subject = "physics",
            grade = 11,
            unitNumber = 3,
            unitTitle = "Law of Conservation of Energy and Momentum",
            topic = "Forces on an Inclined Plane",
            year = "2017",
            questionNumber = 16,
            questionText = "A block of mass m rests on a frictionless plane inclined at an angle θ with the horizontal as shown in the diagram. What is the magnitude of the normal force N exerted by the inclined plane on the block?",
            options = listOf(
                "mg sin θ",
                "mg cos θ",
                "mg tan θ",
                "mg"
            ),
            correctAnswer = 1,
            explanation = "Perpendicular to the inclined plane surface, the block is in equilibrium: ΣF_perpendicular = 0 => N - mg cos θ = 0, so the normal force magnitude is N = mg cos θ.",
            imageResId = R.drawable.img_inclined_plane,
            imageDescription = "Physics free-body diagram of a mass block on an inclined plane with angle theta"
        ),

        // Physics Circuit Resistor Combination Question
        Question(
            id = "phys_2017_27_img",
            subject = "physics",
            grade = 10,
            unitNumber = 4,
            unitTitle = "Electromagnetism",
            topic = "Resistors in Series and Parallel",
            year = "2017",
            questionNumber = 27,
            questionText = "In the circuit diagram shown above, resistor R₁ = 4 Ω is in series with a parallel combination of R₂ = 6 Ω and R₃ = 3 Ω, powered by a 12 V battery. What is the total equivalent resistance of the circuit and the total current I_total supplied by the battery?",
            options = listOf(
                "R_eq = 6 Ω, I_total = 2 A",
                "R_eq = 13 Ω, I_total = 0.92 A",
                "R_eq = 2 Ω, I_total = 6 A",
                "R_eq = 9 Ω, I_total = 1.33 A"
            ),
            correctAnswer = 0,
            explanation = "The parallel resistance of R₂ (6 Ω) and R₃ (3 Ω) is: R_p = (6 * 3) / (6 + 3) = 18 / 9 = 2 Ω. The total equivalent resistance is R_eq = R₁ + R_p = 4 Ω + 2 Ω = 6 Ω. Total current I_total = V / R_eq = 12 V / 6 Ω = 2 A.",
            imageResId = R.drawable.img_resistor_circuit,
            imageDescription = "Schematic circuit diagram showing DC voltage source and series-parallel resistor network"
        ),

        // SAT / Aptitude Geometric Triangle Puzzle Question
        Question(
            id = "apt_2017_36_img",
            subject = "sat",
            grade = 12,
            unitNumber = 1,
            unitTitle = "Analytical and Logical Reasoning",
            topic = "Geometric Reasoning: Triangle Counting",
            year = "2017",
            questionNumber = 36,
            questionText = "Based on the geometric figure shown above (a large triangle ABC divided into smaller triangles ADE, DBF, FCE, and DEF), how many total triangles are present in the figure?",
            options = listOf(
                "4",
                "5",
                "6",
                "7"
            ),
            correctAnswer = 1,
            explanation = "There are 4 small unit triangles (ADE, DBF, FCE, DEF) plus 1 large enclosing triangle (ABC). Total triangles = 4 + 1 = 5.",
            imageResId = R.drawable.img_triangle_puzzle,
            imageDescription = "Geometric diagram of an equilateral triangle partitioned into smaller triangles"
        )
    )
}
