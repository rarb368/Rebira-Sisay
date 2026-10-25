package com.example.data

import com.example.model.Question

object BiologyQuestions2017 {
    fun getQuestions(): List<Question> = listOf(
        Question(
            id = "bio_2017_1",
            subject = "biology",
            grade = 11,
            unitNumber = 1,
            unitTitle = "The Science of Biology",
            topic = "Biological Research in Ethiopia",
            year = "2017",
            questionNumber = 1,
            questionText = "Which one of the following Ethiopian biological research institutes is known for housing a major gene bank in Africa?",
            options = listOf(
                "Addis Ababa University Biology Department",
                "Institute of Biodiversity Conservation",
                "Aklilu Lemma Institute of Pathobiology",
                "Armauer Hansen Research Institute"
            ),
            correctAnswer = 1,
            explanation = "The Ethiopian Institute of Biodiversity Conservation (IBC) houses one of the largest and most prestigious plant genetic gene banks in Africa for conserving indigenous crop varieties and seeds."
        ),
        Question(
            id = "bio_2017_2",
            subject = "biology",
            grade = 9,
            unitNumber = 2,
            unitTitle = "Cell Biology",
            topic = "Microscopy",
            year = "2017",
            questionNumber = 2,
            questionText = "A grade 12 student marked two dots close to each other using a pen on a piece of paper and observed them under a microscope. The two dots appeared well separated under the microscope. This was due to",
            options = listOf("resolution", "magnification.", "staining", "mounting."),
            correctAnswer = 0,
            explanation = "Resolution (resolving power) is the ability of an optical instrument to distinguish between two close adjacent points as separate distinct entities."
        ),
        Question(
            id = "bio_2017_3",
            subject = "biology",
            grade = 9,
            unitNumber = 2,
            unitTitle = "Cell Biology",
            topic = "Cell Organelles",
            year = "2017",
            questionNumber = 3,
            questionText = "From the following alternatives select the common features that are found both in animal and plant cells.",
            options = listOf(
                "Cell membrane, nucleus, cytoplasm, lysosome and Golgi apparatus",
                "Cell membrane, nucleus, cytoplasm, mitochondria and endoplasmic reticulum",
                "Cell membrane, nucleus, cytoplasm, chloroplast and Golgi apparatus",
                "Cell membrane, nucleus, cytoplasm, large vacuole and Golgi apparatus"
            ),
            correctAnswer = 1,
            explanation = "Both plant and animal eukaryotic cells share cell membranes, nuclei, cytoplasm, mitochondria (for cellular respiration), and endoplasmic reticulum."
        ),
        Question(
            id = "bio_2017_4",
            subject = "biology",
            grade = 10,
            unitNumber = 1,
            unitTitle = "Cell Physiology",
            topic = "Osmosis and Tonicity",
            year = "2017",
            questionNumber = 4,
            questionText = "A teacher ordered group I to place a slice of potato in hypertonic solution and group II in hypotonic solution. What will happen to the potato slices?",
            options = listOf(
                "turgid while for group II it will be flaccid.",
                "haemolyzed while for group II it will be flaccid.",
                "flaccid while for group II it will be turgid.",
                "turgid while for group II there will be no change."
            ),
            correctAnswer = 2,
            explanation = "In a hypertonic solution, water leaves plant cells by osmosis causing plasmolysis (cells become flaccid). In a hypotonic solution, water enters plant cells, making them turgid."
        ),
        Question(
            id = "bio_2017_5",
            subject = "biology",
            grade = 9,
            unitNumber = 3,
            unitTitle = "Human Nutrition",
            topic = "Balanced Diet",
            year = "2017",
            questionNumber = 5,
            questionText = "A man eats only vegetables, beans, banana and orange for one week continuously in his lunch and dinner. What nutrients are most likely missed in his meal to be a balanced diet?",
            options = listOf("Carbohydrates", "Lipids", "Proteins", "Minerals"),
            correctAnswer = 1,
            explanation = "Vegetables, fruits, and legumes provide carbohydrates, plant proteins, vitamins, and minerals, but lack adequate fats (lipids), which are vital for cell membranes and fat-soluble vitamin absorption."
        ),
        Question(
            id = "bio_2017_6",
            subject = "biology",
            grade = 11,
            unitNumber = 3,
            unitTitle = "Human Body Systems",
            topic = "Digestive System",
            year = "2017",
            questionNumber = 6,
            questionText = "Which of the following is correct about the process of digestion or absorption of food in the small intestine?",
            options = listOf(
                "Proteins are converted into amino acids by the action of hydrochloric acid and amylase.",
                "Bile emulsifies fats to increase the surface area to make it easier for lipase.",
                "Water is reabsorbed into the small intestine and metabolic wastes are excreted.",
                "Glucose and non-hydrolyzed starch molecules are absorbed through the lacteal."
            ),
            correctAnswer = 1,
            explanation = "Bile produced by the liver and stored in the gallbladder physically breaks down large fat globules into microscopic droplets (emulsification), increasing surface area for enzymatic attack by lipase."
        ),
        Question(
            id = "bio_2017_7",
            subject = "biology",
            grade = 11,
            unitNumber = 2,
            unitTitle = "Biochemical Molecules",
            topic = "Enzyme Action and Food Tests",
            year = "2017",
            questionNumber = 7,
            questionText = "Students wanted to prove that enzymatic digestion begins in the mouth using chewed food coated with saliva. What food sample and testing solution should they use?",
            options = listOf(
                "Potato and iodine solution",
                "Meat and Benedict's solution",
                "Fish and sodium hydroxide solution",
                "Bread and copper sulphate solution"
            ),
            correctAnswer = 0,
            explanation = "Salivary amylase digests starch in potato/bread into maltose. Testing potato starch with iodine solution before and after saliva digestion demonstrates the breakdown of starch (loss of blue-black color)."
        ),
        Question(
            id = "bio_2017_8",
            subject = "biology",
            grade = 10,
            unitNumber = 2,
            unitTitle = "Respiratory System",
            topic = "Gas Exchange in Humans",
            year = "2017",
            questionNumber = 8,
            questionText = "Compared to inhaled air, what are the percentages of nitrogen, oxygen and carbon dioxide gases in exhaled air, respectively?",
            options = listOf("80%, 16% and 4%", "80%, 4% and 16%", "16%, 4% and 80%", "4%, 16% and 80%"),
            correctAnswer = 0,
            explanation = "Exhaled air consists roughly of ~78-80% nitrogen (unchanged), ~16% oxygen (decreased from 21%), and ~4% carbon dioxide (increased from 0.04%)."
        ),
        Question(
            id = "bio_2017_11",
            subject = "biology",
            grade = 10,
            unitNumber = 3,
            unitTitle = "Circulatory System",
            topic = "Blood Components",
            year = "2017",
            questionNumber = 11,
            questionText = "If your body cannot make enough red blood cells, which one of the following mineral rich foods can correct this problem?",
            options = listOf("Iron-rich food", "Calcium-rich food", "Iodine-rich food", "Sodium-rich food"),
            correctAnswer = 0,
            explanation = "Iron is the central mineral cofactor in the heme group of hemoglobin required for red blood cell synthesis in bone marrow; iron deficiency leads to anemia."
        ),
        Question(
            id = "bio_2017_12",
            subject = "biology",
            grade = 9,
            unitNumber = 1,
            unitTitle = "Introduction to Biology",
            topic = "Binomial Nomenclature",
            year = "2017",
            questionNumber = 12,
            questionText = "In which of the following ways should the students write the correct scientific names of human being, dog and housefly, respectively?",
            options = listOf(
                "Homo Sapiens, Canis Familiaris and Musca Domestica",
                "Homo sapiens, Canis familiaris and Musca domestica",
                "homo sapiens, Canis familiaris and Musca domestica",
                "Homo sapiens, Canis familiaris and musca domestica"
            ),
            correctAnswer = 1,
            explanation = "Under the rules of binomial nomenclature, the genus name begins with an uppercase letter, and the species epithet begins with a lowercase letter, italicized or underlined: Homo sapiens, Canis familiaris, Musca domestica."
        ),
        Question(
            id = "bio_2017_13",
            subject = "biology",
            grade = 11,
            unitNumber = 4,
            unitTitle = "Plant Biology",
            topic = "Plant Classification",
            year = "2017",
            questionNumber = 13,
            questionText = "Identify the typical examples for division Gymnospermae and Angiospermae, respectively?",
            options = listOf("Mosses and ferns", "Liverworts and palm trees", "Grasses and legumes", "Juniper and roses"),
            correctAnswer = 3,
            explanation = "Juniper (conifer, gymnosperm with naked seeds) and roses (flowering plants with enclosed seeds in fruit, angiosperm) are typical examples."
        ),
        Question(
            id = "bio_2017_14",
            subject = "biology",
            grade = 11,
            unitNumber = 4,
            unitTitle = "Plant Biology",
            topic = "Monocots vs Dicots",
            year = "2017",
            questionNumber = 14,
            questionText = "A student found plants containing a single cotyledon, long parallel-veined leaves, and scattered vascular bundles in stems. Which option contains plants in this group?",
            options = listOf("Grasses, orchids, teff and roses", "Grasses, orchids, teff and beans", "Grasses, sunflowers, teff and maize", "Grasses, orchids, teff and maize"),
            correctAnswer = 3,
            explanation = "These anatomical characteristics define Monocotyledons (monocots). Grasses, orchids, teff, and maize (corn) are all monocots."
        ),
        Question(
            id = "bio_2017_16",
            subject = "biology",
            grade = 10,
            unitNumber = 4,
            unitTitle = "Ecology",
            topic = "Food Chains and Webs",
            year = "2017",
            questionNumber = 16,
            questionText = "Based on the food chain: Leaves -> Grasshoppers -> Rodents -> Leopards. What will happen if the number of rodents significantly decrease?",
            options = listOf(
                "an increase in the number of leopards.",
                "fewer grasshoppers in the chain.",
                "no change in the number of leopard.",
                "an increase in the number of grasshoppers."
            ),
            correctAnswer = 3,
            explanation = "If the rodent population crashes, there will be reduced predation pressure on grasshoppers, resulting in a population increase of grasshoppers."
        ),
        Question(
            id = "bio_2017_17",
            subject = "biology",
            grade = 10,
            unitNumber = 4,
            unitTitle = "Ecology",
            topic = "Biogeochemical Cycles",
            year = "2017",
            questionNumber = 17,
            questionText = "Which of the following is NOT a key process involved in the carbon cycle?",
            options = listOf("Evaporation", "Cellular respiration", "Photosynthesis", "Decomposition"),
            correctAnswer = 0,
            explanation = "Evaporation is a physical phase-change process in the hydrologic (water) cycle, whereas photosynthesis, cellular respiration, combustion, and decomposition are central to the carbon cycle."
        ),
        Question(
            id = "bio_2017_18",
            subject = "biology",
            grade = 11,
            unitNumber = 2,
            unitTitle = "Cell Division",
            topic = "Mitosis",
            year = "2017",
            questionNumber = 18,
            questionText = "Which of the following is correct about mitosis? It is a division of",
            options = listOf(
                "somatic cells to make daughter cells with half chromosome.",
                "sex cells to make identical daughter cells with doubled chromosome.",
                "somatic cells to make identical daughter cells.",
                "sex cells to make different daughter cells."
            ),
            correctAnswer = 2,
            explanation = "Mitosis occurs in somatic (body) cells and produces two genetically identical daughter cells maintaining the exact diploid chromosome number."
        ),
        Question(
            id = "bio_2017_20",
            subject = "biology",
            grade = 11,
            unitNumber = 4,
            unitTitle = "Plant Biology",
            topic = "Leaf Anatomy",
            year = "2017",
            questionNumber = 20,
            questionText = "Which tissues of plant leaves are involved in waterproofing, major photosynthetic and major gas exchange activities, respectively?",
            options = listOf(
                "Guard cells, palisade mesophyll and epidermis",
                "Guard cells, palisade mesophyll and spongy mesophyll",
                "Waxy cuticle, palisade mesophyll and spongy mesophyll",
                "Waxy cuticle, palisade mesophyll and epidermis"
            ),
            correctAnswer = 2,
            explanation = "The waxy cuticle prevents excessive water loss, the palisade mesophyll contains densely packed chloroplasts for light harvesting, and the spongy mesophyll has loose air spaces for gas exchange."
        ),
        Question(
            id = "bio_2017_22",
            subject = "biology",
            grade = 11,
            unitNumber = 4,
            unitTitle = "Plant Biology",
            topic = "Plant Hormones",
            year = "2017",
            questionNumber = 22,
            questionText = "What will happen to the growth of the plant when you remove indole-acetic acid (auxin) from the main apical shoot tip of the plant?",
            options = listOf("The plant will bush out.", "It stimulates dropping of leaves.", "The main shoot of the plant elongates.", "The growth of side shoots will slow down."),
            correctAnswer = 0,
            explanation = "Apical dominance is maintained by auxins produced at the apical bud. Decapitating the tip removes this inhibition, stimulating lateral dormant buds to sprout, making the plant bushy."
        ),
        Question(
            id = "bio_2017_26",
            subject = "biology",
            grade = 11,
            unitNumber = 2,
            unitTitle = "Biochemical Molecules",
            topic = "Enzymes",
            year = "2017",
            questionNumber = 26,
            questionText = "Which one of the following statements is correct about the catalytic activities of enzymes? They",
            options = listOf(
                "change the overall nature of the products.",
                "allow reactions in cells to take place quickly.",
                "alter themselves at the end of biochemical reactions.",
                "increase the energy required to start a reaction."
            ),
            correctAnswer = 1,
            explanation = "Enzymes are biological catalysts that lower activation energy, greatly speeding up metabolic reactions without being consumed or altering reaction equilibria."
        ),
        Question(
            id = "bio_2017_28",
            subject = "biology",
            grade = 11,
            unitNumber = 2,
            unitTitle = "Biochemical Molecules",
            topic = "Induced-Fit Model",
            year = "2017",
            questionNumber = 28,
            questionText = "How does the induced-fit model explain enzyme specificity? They",
            options = listOf(
                "change the shape of active site slightly to fit substrates precisely.",
                "have a rigid active site that can just bind to single substrate.",
                "have multiple active sites that can fit to different substrates.",
                "bind to whatever the shape of substrate that fits to allosteric site."
            ),
            correctAnswer = 0,
            explanation = "In Koshland's induced-fit model, the active site is flexible: binding of the substrate induces a conformational shift that molds the active site snugly around the substrate."
        ),
        Question(
            id = "bio_2017_34",
            subject = "biology",
            grade = 12,
            unitNumber = 1,
            unitTitle = "Genetics and Molecular Biology",
            topic = "Nucleic Acids",
            year = "2017",
            questionNumber = 34,
            questionText = "Which structural and functional difference is observed between DNA and RNA?",
            options = listOf(
                "Both DNA and RNA have the same nitrogenous bases but differ in pentose sugar.",
                "RNA produces DNA through the process of transcription.",
                "RNA stores the genetic material while DNA involves in protein synthesis.",
                "DNA is very stable molecule while RNA is degraded quickly."
            ),
            correctAnswer = 3,
            explanation = "Due to the absence of the reactive 2'-OH hydroxyl group on its deoxyribose backbone and its double-helical structure, DNA is chemically far more stable than single-stranded RNA."
        ),
        Question(
            id = "bio_2017_36",
            subject = "biology",
            grade = 12,
            unitNumber = 1,
            unitTitle = "Genetics and Molecular Biology",
            topic = "Mendelian Genetics",
            year = "2017",
            questionNumber = 36,
            questionText = "Suppose axial (AA) was crossed with terminal (aa) to produce F1 (Aa). What will be the number of heterozygous dominant individuals if 1000 offspring are produced by selfing F1?",
            options = listOf("200", "750", "500", "250"),
            correctAnswer = 2,
            explanation = "Selfing Aa x Aa gives genotypic ratio 1 AA : 2 Aa : 1 aa (50% heterozygous Aa). For 1000 offspring, 1000 * 0.50 = 500."
        ),
        Question(
            id = "bio_2017_41",
            subject = "biology",
            grade = 12,
            unitNumber = 2,
            unitTitle = "Reproduction and Development",
            topic = "Menstrual Cycle Hormones",
            year = "2017",
            questionNumber = 41,
            questionText = "If progesterone remains high after ovulation, what will be the most likely outcome?",
            options = listOf(
                "FSH will be produced rapidly which leads to a new cycle.",
                "Oestrogen will be decreased, closing down the lining of the uterus.",
                "Uterus lining will be maintained and prepared for pregnancy.",
                "Corpus luteum will be regenerated, inhibiting hormone production."
            ),
            correctAnswer = 2,
            explanation = "Progesterone maintains the vascularized secretory endometrium of the uterus, preventing menstruation and sustaining implantation during pregnancy."
        ),
        Question(
            id = "bio_2017_47",
            subject = "biology",
            grade = 10,
            unitNumber = 5,
            unitTitle = "Conservation of Natural Resources",
            topic = "Renewable Resources",
            year = "2017",
            questionNumber = 47,
            questionText = "Select the option where the whole list contains renewable natural resources.",
            options = listOf(
                "Coal, oil, natural gas and minerals",
                "Coal, wind, hydro power and biomass",
                "Sunlight, wind, hydro power and biomass",
                "Sunlight, wind, hydro power and natural gas"
            ),
            correctAnswer = 2,
            explanation = "Sunlight, wind energy, hydroelectric power, and organic biomass are continuously replenished by natural ecological cycles and do not deplete."
        ),
        Question(
            id = "bio_2017_67",
            subject = "biology",
            grade = 12,
            unitNumber = 3,
            unitTitle = "Energy Transformation",
            topic = "Photosynthesis Mechanisms",
            year = "2017",
            questionNumber = 67,
            questionText = "What are the roles of grana and stroma in the process of photosynthesis? Grana is a site for",
            options = listOf(
                "cyclic pathway while stroma is a site for non-cyclic pathway.",
                "photolysis of water while stroma is a site for carbon fixation.",
                "generation of sugar while stroma is a site for photolysis of water.",
                "production of NADPH while stroma is a site for chemiosmosis."
            ),
            correctAnswer = 1,
            explanation = "The thylakoid grana host the light-dependent reactions where photolysis of water and ATP/NADPH generation occur, whereas the stroma contains enzymes for the Calvin cycle (carbon fixation)."
        ),
        Question(
            id = "bio_2017_72",
            subject = "biology",
            grade = 12,
            unitNumber = 3,
            unitTitle = "Energy Transformation",
            topic = "Cellular Respiration",
            year = "2017",
            questionNumber = 72,
            questionText = "Which of the following is the difference between substrate level phosphorylation and oxidative phosphorylation?",
            options = listOf(
                "Substrate level occurs in inner membrane while oxidative occurs in cytosol.",
                "Substrate level occurs during glycolysis and Krebs cycle while oxidative occurs during electron transport chain.",
                "Has the same source of energy as oxidative phosphorylation.",
                "Occurs in aerobic respiration while oxidative in anaerobic respiration."
            ),
            correctAnswer = 1,
            explanation = "Substrate-level phosphorylation directly transfers a phosphate from an organic intermediate to ADP during glycolysis and the citric acid cycle; oxidative phosphorylation uses the proton gradient in the electron transport chain."
        ),
        Question(
            id = "bio_2017_100",
            subject = "biology",
            grade = 10,
            unitNumber = 5,
            unitTitle = "Conservation of Natural Resources",
            topic = "Climate Change Mitigation in Ethiopia",
            year = "2017",
            questionNumber = 100,
            questionText = "Which one of the following local community practices in Ethiopia is considered as an environmental action that addresses climate change mitigation?",
            options = listOf(
                "Culture of respecting humanity that promotes eco-tourism industry",
                "Negotiating mechanism that is led by elders to reduce social unrest",
                "Planting millions of seedlings following the green legacy initiative",
                "Treating different diseases with herbal remedies by traditional healers"
            ),
            correctAnswer = 2,
            explanation = "The Ethiopian Green Legacy Initiative mobilizes mass community seedling planting campaigns nationwide to reforest the highlands, sequester carbon dioxide, and mitigate climate change."
        )
    )
}
