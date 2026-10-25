package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ExamDataManager
import com.example.data.QuestionRepository
import com.example.model.ExamSubject
import com.example.model.UnitSummary
import com.example.ui.components.CircularScoreRing
import com.example.ui.components.ExamModeSelectionDialog
import com.example.ui.theme.AccentPink
import com.example.ui.theme.TealPrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectExamListScreen(
    subject: ExamSubject,
    dataManager: ExamDataManager,
    questionRepository: QuestionRepository,
    onBack: () -> Unit,
    onStartExam: (year: String, isUnitPractice: Boolean, grade: Int, unitTitle: String, revealMode: String) -> Unit
) {
    // Tabs matching screenshot: All, 2017 Na, 2016 Na, 2015 Na, 2015 So, 2014/15 Na, 2014/15 So
    val tabs = listOf("All", "2017 Na", "2016 Na", "2015 Na", "2015 So", "2014/15 Na", "2014/15 So")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Mode toggle: Full Exam Papers vs Practice by Unit
    var viewMode by remember { mutableStateOf("full_exam") } // "full_exam" or "by_unit"
    var selectedGradeFilter by remember { mutableStateOf<Int?>(null) } // null = All grades

    // Dialog state
    var selectedExamToStart by remember { mutableStateOf<Pair<String, Boolean>?>(null) }
    var selectedUnitToStart by remember { mutableStateOf<UnitSummary?>(null) }

    val activeYearFilter = when (selectedTabIndex) {
        0 -> "All"
        1 -> "2017"
        2 -> "2016"
        3, 4 -> "2015"
        else -> "2014/15"
    }

    val examYearsList = if (activeYearFilter == "All") {
        listOf("2017", "2016", "2015", "2014/15")
    } else {
        listOf(activeYearFilter)
    }

    val availableUnits = remember(subject, selectedGradeFilter, activeYearFilter) {
        questionRepository.getUnitsForSubject(subject.id, selectedGradeFilter, if (activeYearFilter == "All") null else activeYearFilter)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = subject.title.lowercase().replaceFirstChar { it.uppercase() },
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("subject_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Toggle between Full Exam and Practice by Unit
                        viewMode = if (viewMode == "full_exam") "by_unit" else "full_exam"
                    }) {
                        Icon(
                            imageVector = if (viewMode == "full_exam") Icons.Default.CollectionsBookmark else Icons.Default.Assignment,
                            contentDescription = "Switch View Mode",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TealPrimary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF1F5F9)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Horizontal Year Tabs matching Ethio Matric screenshots
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = TealPrimary,
                contentColor = Color.White,
                edgePadding = 12.dp,
                indicator = { tabPositions ->
                    if (selectedTabIndex < tabPositions.size) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.BottomStart)
                                .offset(x = tabPositions[selectedTabIndex].left)
                                .width(tabPositions[selectedTabIndex].width)
                                .height(4.dp)
                                .background(AccentPink)
                        )
                    }
                },
                divider = {}
            ) {
                tabs.forEachIndexed { index, tabName ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = tabName,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }

            // View Mode Selector Bar: Full Examination vs Practice by Unit
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { viewMode = "full_exam" },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (viewMode == "full_exam") TealPrimary else Color(0xFF64748B)
                    )
                ) {
                    Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Full Exams", fontWeight = if (viewMode == "full_exam") FontWeight.Bold else FontWeight.Normal)
                }

                TextButton(
                    onClick = { viewMode = "by_unit" },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (viewMode == "by_unit") TealPrimary else Color(0xFF64748B)
                    )
                ) {
                    Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Practice by Unit", fontWeight = if (viewMode == "by_unit") FontWeight.Bold else FontWeight.Normal)
                }
            }

            if (viewMode == "full_exam") {
                // List of Full Exam Cards
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(examYearsList) { year ->
                        val questions = questionRepository.getQuestionsForFullExam(subject.id, year)
                        val lastAttempt = dataManager.getAllAttempts().firstOrNull {
                            it.subject.equals(subject.id, ignoreCase = true) && it.year == year && !it.isUnitPractice
                        }

                        val score = lastAttempt?.scorePercentage?.toFloat() ?: 0f
                        val hasPracticed = lastAttempt != null

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { selectedExamToStart = Pair(year, false) }
                                .testTag("exam_card_$year"),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                // Green header bar matching screenshot
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(TealPrimary)
                                        .padding(horizontal = 16.dp, vertical = 10.dp)
                                ) {
                                    Text(
                                        text = "$year - ${if (subject == ExamSubject.MATHS_SOC) "Social" else "Natural"}",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }

                                // Card Body with Circular Score Ring and details
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CircularScoreRing(
                                        percentage = score,
                                        sizeDp = 92,
                                        strokeWidthDp = 7,
                                        activeColor = AccentPink
                                    )

                                    Spacer(modifier = Modifier.width(18.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        ExamDetailRow("Total Questions:", "${questions.size.coerceAtLeast(subject.totalDefaultQuestions)}")
                                        ExamDetailRow("Given Time:", formatMinutes(subject.defaultDurationMinutes))
                                        ExamDetailRow(
                                            "Last Practiced:",
                                            if (hasPracticed) SimpleDateFormat("dd/MMMM", Locale.US).format(Date(lastAttempt.completedAt)) else "Not yet practiced"
                                        )
                                        ExamDetailRow("Exam Year:", "$year July EC")
                                    }
                                }
                            }
                        }
                    }

                    item {
                        // Hint at bottom matching screenshot
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Swipe or tap 'Practice by Unit' to practice by chapter",
                                fontSize = 13.sp,
                                color = Color(0xFF64748B),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            } else {
                // Practice by Unit View
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    // Grade Filter chips (Grade 9, 10, 11, 12, All)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedGradeFilter == null,
                            onClick = { selectedGradeFilter = null },
                            label = { Text("All Grades", fontSize = 12.sp) }
                        )
                        listOf(9, 10, 11, 12).forEach { grade ->
                            FilterChip(
                                selected = selectedGradeFilter == grade,
                                onClick = { selectedGradeFilter = if (selectedGradeFilter == grade) null else grade },
                                label = { Text("G$grade", fontSize = 12.sp) }
                            )
                        }
                    }

                    if (availableUnits.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No units found for selected criteria",
                                color = Color(0xFF64748B),
                                fontSize = 14.sp
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(availableUnits) { unit ->
                                val participation = dataManager.getUnitParticipation(subject.id, unit.grade, unit.unitTitle)

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable { selectedUnitToStart = unit }
                                        .testTag("unit_card_G${unit.grade}_U${unit.unitNumber}"),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Surface(
                                                    color = TealPrimary.copy(alpha = 0.12f),
                                                    shape = RoundedCornerShape(6.dp)
                                                ) {
                                                    Text(
                                                        text = "G${unit.grade} U${unit.unitNumber}",
                                                        color = TealPrimary,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 12.sp,
                                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(10.dp))
                                                Text(
                                                    text = unit.unitTitle,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 15.sp,
                                                    color = Color(0xFF0F172A)
                                                )
                                            }

                                            Text(
                                                text = "${unit.totalQuestions} ques.",
                                                fontSize = 13.sp,
                                                color = Color(0xFF64748B),
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(10.dp))

                                        // Participation Green Progress Bar matching screenshot
                                        Column {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = "Mastery Level",
                                                    fontSize = 11.sp,
                                                    color = Color(0xFF64748B)
                                                )
                                                Text(
                                                    text = "${(participation * 100).toInt()}%",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF16A34A)
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            LinearProgressIndicator(
                                                progress = { participation },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(6.dp)
                                                    .clip(RoundedCornerShape(3.dp)),
                                                color = Color(0xFF22C55E),
                                                trackColor = Color(0xFFE2E8F0)
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Time: ${unit.totalQuestions * 2} mins (2 min/q)",
                                                fontSize = 11.sp,
                                                color = Color(0xFF475569)
                                            )
                                            Button(
                                                onClick = { selectedUnitToStart = unit },
                                                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                                                shape = RoundedCornerShape(8.dp),
                                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                            ) {
                                                Text("Practice Unit", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Exam Mode Dialog for Full Exam
    selectedExamToStart?.let { (year, isUnitPractice) ->
        ExamModeSelectionDialog(
            examTitle = "${subject.title} $year Full Examination",
            onDismiss = { selectedExamToStart = null },
            onSelectMode = { mode ->
                selectedExamToStart = null
                onStartExam(year, isUnitPractice, 12, "", mode)
            }
        )
    }

    // Exam Mode Dialog for Unit Practice
    selectedUnitToStart?.let { unit ->
        ExamModeSelectionDialog(
            examTitle = "Grade ${unit.grade} Unit ${unit.unitNumber}: ${unit.unitTitle}",
            onDismiss = { selectedUnitToStart = null },
            onSelectMode = { mode ->
                val unitToLaunch = selectedUnitToStart
                selectedUnitToStart = null
                if (unitToLaunch != null) {
                    onStartExam(activeYearFilter, true, unitToLaunch.grade, unitToLaunch.unitTitle, mode)
                }
            }
        )
    }
}

@Composable
private fun ExamDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = Color(0xFF64748B))
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
    }
}

private fun formatMinutes(totalMinutes: Int): String {
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return if (minutes == 0) "$hours:00" else "$hours:$minutes"
}
