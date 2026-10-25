package com.example.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ExamDataManager
import com.example.data.QuestionRepository
import com.example.model.ExamAttempt
import com.example.model.ExamSubject
import com.example.model.Question
import com.example.model.StudentProfile
import com.example.ui.theme.TealPrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    dataManager: ExamDataManager,
    questionRepository: QuestionRepository,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var isAuthenticated by remember { mutableStateOf(false) }
    var passwordInput by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // Admin Tabs: 0 = Students & Exams, 1 = Question Bank
    var adminTab by remember { mutableIntStateOf(0) }

    // Students state
    var searchQuery by remember { mutableStateOf("") }
    var sortOption by remember { mutableStateOf("date") } // "date", "name", "score"
    var selectedStudentForDetails by remember { mutableStateOf<StudentProfile?>(null) }

    // Questions state
    var selectedSubjectFilter by remember { mutableStateOf("chemistry") }
    var selectedGradeFilter by remember { mutableStateOf<Int?>(null) }
    var questionBeingEdited by remember { mutableStateOf<Question?>(null) }
    var showAddQuestionDialog by remember { mutableStateOf(false) }

    val allStudents = remember(dataManager) { dataManager.getAllRegisteredStudents() }
    val allAttempts = remember(dataManager) { dataManager.getAllAttempts() }

    val filteredStudents = remember(searchQuery, sortOption, allStudents, allAttempts) {
        val filtered = allStudents.filter { s ->
            s.fullName.contains(searchQuery, ignoreCase = true) ||
                    s.email.contains(searchQuery, ignoreCase = true) ||
                    s.phone.contains(searchQuery, ignoreCase = true)
        }

        when (sortOption) {
            "name" -> filtered.sortedBy { it.fullName }
            "score" -> filtered.sortedByDescending { s ->
                val sAttempts = allAttempts.filter { it.studentEmail.equals(s.email, ignoreCase = true) }
                if (sAttempts.isNotEmpty()) sAttempts.map { it.scorePercentage }.average() else 0.0
            }
            else -> filtered.sortedByDescending { it.registeredAt }
        }
    }

    if (!isAuthenticated) {
        // Password Login Screen
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Admin Authorization", fontWeight = FontWeight.Bold, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = TealPrimary)
                )
            },
            containerColor = Color(0xFFF1F5F9)
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.size(60.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = TealPrimary.copy(alpha = 0.15f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(32.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("3RM Admin Portal", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0F172A))
                        Text(
                            "Authorized Ambo Ifa Boru secondary school administrative access only.",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B),
                            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                        )

                        OutlinedTextField(
                            value = passwordInput,
                            onValueChange = {
                                passwordInput = it
                                passwordError = null
                            },
                            label = { Text("Admin Password") },
                            leadingIcon = { Icon(Icons.Default.Key, contentDescription = null) },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            isError = passwordError != null,
                            supportingText = passwordError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("admin_password_input")
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                if (passwordInput == "@Rabira1818") {
                                    isAuthenticated = true
                                    passwordError = null
                                } else {
                                    passwordError = "Incorrect admin password."
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("admin_login_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("LOGIN TO ADMIN", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    } else {
        // Authenticated Admin Dashboard
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("3RM Admin Portal", fontWeight = FontWeight.Bold, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                isAuthenticated = false
                                passwordInput = ""
                            },
                            modifier = Modifier.testTag("admin_logout_button")
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = TealPrimary)
                )
            },
            containerColor = Color(0xFFF1F5F9)
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Top Tab Bar (Students vs Question Bank)
                TabRow(
                    selectedTabIndex = adminTab,
                    containerColor = Color.White,
                    contentColor = TealPrimary
                ) {
                    Tab(
                        selected = adminTab == 0,
                        onClick = { adminTab = 0 },
                        text = { Text("Students & Scores (${allStudents.size})", fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = adminTab == 1,
                        onClick = { adminTab = 1 },
                        text = { Text("Question Bank", fontWeight = FontWeight.Bold) }
                    )
                }

                if (adminTab == 0) {
                    // TAB 1: Students & Exam Records
                    Column(modifier = Modifier.fillMaxSize().padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Search by name, email, phone...") },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("admin_search_students")
                            )

                            Button(
                                onClick = {
                                    val csvData = dataManager.generateStudentsCsv()
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, csvData)
                                        putExtra(Intent.EXTRA_TITLE, "3RM_AIBSBSS_Students.csv")
                                        type = "text/csv"
                                    }
                                    context.startActivity(Intent.createChooser(sendIntent, "Export Students CSV"))
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("export_csv_button")
                            ) {
                                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("CSV", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Sorting chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Sort by:", fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.align(Alignment.CenterVertically))
                            FilterChip(
                                selected = sortOption == "date",
                                onClick = { sortOption = "date" },
                                label = { Text("Recent Date", fontSize = 11.sp) }
                            )
                            FilterChip(
                                selected = sortOption == "name",
                                onClick = { sortOption = "name" },
                                label = { Text("Name", fontSize = 11.sp) }
                            )
                            FilterChip(
                                selected = sortOption == "score",
                                onClick = { sortOption = "score" },
                                label = { Text("Score", fontSize = 11.sp) }
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Student Cards List
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(filteredStudents) { student ->
                                val sAttempts = allAttempts.filter { it.studentEmail.equals(student.email, ignoreCase = true) }
                                val avgScore = if (sAttempts.isNotEmpty()) {
                                    "%.1f%%".format(sAttempts.map { it.scorePercentage }.average())
                                } else {
                                    "No exams yet"
                                }

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedStudentForDetails = student }
                                        .testTag("student_record_${student.id}"),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = student.fullName,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.sp,
                                                color = Color(0xFF0F172A)
                                            )
                                            Surface(
                                                color = TealPrimary.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(6.dp)
                                            ) {
                                                Text(
                                                    text = avgScore,
                                                    color = TealPrimary,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 12.sp,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Gmail: ${student.email}", fontSize = 12.sp, color = Color(0xFF475569))
                                        Text(text = "Phone: ${student.phone}", fontSize = 12.sp, color = Color(0xFF475569))
                                        Text(
                                            text = "Registered: ${SimpleDateFormat("dd MMM yyyy", Locale.US).format(Date(student.registeredAt))} • ${sAttempts.size} exams taken",
                                            fontSize = 11.sp,
                                            color = Color(0xFF94A3B8)
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // TAB 2: Question Bank Manager
                    Column(modifier = Modifier.fillMaxSize().padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Question Bank (${questionRepository.getAllQuestions().size} total)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Button(
                                onClick = { showAddQuestionDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("admin_add_question_button")
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add Question", fontSize = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Subject Filter chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            ExamSubject.entries.take(4).forEach { sub ->
                                FilterChip(
                                    selected = selectedSubjectFilter == sub.id,
                                    onClick = { selectedSubjectFilter = sub.id },
                                    label = { Text(sub.title, fontSize = 10.sp) }
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            ExamSubject.entries.drop(4).forEach { sub ->
                                FilterChip(
                                    selected = selectedSubjectFilter == sub.id,
                                    onClick = { selectedSubjectFilter = sub.id },
                                    label = { Text(sub.title, fontSize = 10.sp) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        val qList = questionRepository.getQuestionsBySubject(selectedSubjectFilter)

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(qList) { q ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Q${q.questionNumber} • G${q.grade} U${q.unitNumber}: ${q.unitTitle}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = TealPrimary
                                            )
                                            Row {
                                                IconButton(
                                                    onClick = { questionBeingEdited = q },
                                                    modifier = Modifier.size(28.dp)
                                                ) {
                                                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF2563EB), modifier = Modifier.size(16.dp))
                                                }
                                                IconButton(
                                                    onClick = { questionRepository.deleteQuestion(q.id) },
                                                    modifier = Modifier.size(28.dp)
                                                ) {
                                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                                                }
                                            }
                                        }

                                        Text(
                                            text = q.questionText,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF1E293B),
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )

                                        Text(
                                            text = "Answer: ${('A' + q.correctAnswer)} (${q.options.getOrNull(q.correctAnswer) ?: ""})",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF16A34A)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Student Exam Details Dialog
    selectedStudentForDetails?.let { student ->
        val attempts = allAttempts.filter { it.studentEmail.equals(student.email, ignoreCase = true) }

        AlertDialog(
            onDismissRequest = { selectedStudentForDetails = null },
            title = {
                Text(student.fullName, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text("Email: ${student.email}", fontSize = 12.sp, color = Color(0xFF64748B))
                    Text("Phone: ${student.phone}", fontSize = 12.sp, color = Color(0xFF64748B))

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Exam Attempts (${attempts.size}):", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(6.dp))

                    if (attempts.isEmpty()) {
                        Text("No exams attempted yet.", fontSize = 12.sp, color = Color(0xFF94A3B8))
                    } else {
                        attempts.forEach { att ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "${att.subject.uppercase()} (${att.year})",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                        Text(
                                            text = "%.1f%%".format(att.scorePercentage),
                                            fontWeight = FontWeight.ExtraBold,
                                            color = if (att.scorePercentage >= 50) Color(0xFF16A34A) else Color(0xFFDC2626)
                                        )
                                    }
                                    Text(
                                        text = "Score: ${att.correctCount}/${att.totalQuestions} • Time: ${att.timeTakenSeconds / 60}m",
                                        fontSize = 11.sp,
                                        color = Color(0xFF64748B)
                                    )
                                    Text(
                                        text = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US).format(Date(att.completedAt)),
                                        fontSize = 10.sp,
                                        color = Color(0xFF94A3B8)
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedStudentForDetails = null }) {
                    Text("Close")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    // Add / Edit Question Dialog
    if (showAddQuestionDialog || questionBeingEdited != null) {
        val editing = questionBeingEdited
        QuestionEditorDialog(
            initialQuestion = editing,
            defaultSubject = selectedSubjectFilter,
            onDismiss = {
                showAddQuestionDialog = false
                questionBeingEdited = null
            },
            onSave = { savedQ ->
                if (editing != null) {
                    questionRepository.updateQuestion(savedQ)
                } else {
                    questionRepository.addQuestion(savedQ)
                }
                showAddQuestionDialog = false
                questionBeingEdited = null
            }
        )
    }
}

@Composable
private fun QuestionEditorDialog(
    initialQuestion: Question?,
    defaultSubject: String,
    onDismiss: () -> Unit,
    onSave: (Question) -> Unit
) {
    var subject by remember { mutableStateOf(initialQuestion?.subject ?: defaultSubject) }
    var gradeStr by remember { mutableStateOf(initialQuestion?.grade?.toString() ?: "12") }
    var unitNumStr by remember { mutableStateOf(initialQuestion?.unitNumber?.toString() ?: "1") }
    var unitTitle by remember { mutableStateOf(initialQuestion?.unitTitle ?: "") }
    var topic by remember { mutableStateOf(initialQuestion?.topic ?: "") }
    var year by remember { mutableStateOf(initialQuestion?.year ?: "2017") }
    var questionNumberStr by remember { mutableStateOf(initialQuestion?.questionNumber?.toString() ?: "1") }
    var questionText by remember { mutableStateOf(initialQuestion?.questionText ?: "") }
    var optA by remember { mutableStateOf(initialQuestion?.options?.getOrNull(0) ?: "") }
    var optB by remember { mutableStateOf(initialQuestion?.options?.getOrNull(1) ?: "") }
    var optC by remember { mutableStateOf(initialQuestion?.options?.getOrNull(2) ?: "") }
    var optD by remember { mutableStateOf(initialQuestion?.options?.getOrNull(3) ?: "") }
    var correctIndex by remember { mutableIntStateOf(initialQuestion?.correctAnswer ?: 0) }
    var explanation by remember { mutableStateOf(initialQuestion?.explanation ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (initialQuestion != null) "Edit Question" else "Add New Question", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject (e.g. chemistry)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(
                        value = gradeStr,
                        onValueChange = { gradeStr = it },
                        label = { Text("Grade (9-12)") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = unitNumStr,
                        onValueChange = { unitNumStr = it },
                        label = { Text("Unit No.") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = unitTitle,
                    onValueChange = { unitTitle = it },
                    label = { Text("Unit Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = topic,
                    onValueChange = { topic = it },
                    label = { Text("Topic") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = questionText,
                    onValueChange = { questionText = it },
                    label = { Text("Question Text") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = optA, onValueChange = { optA = it }, label = { Text("Option A") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = optB, onValueChange = { optB = it }, label = { Text("Option B") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = optC, onValueChange = { optC = it }, label = { Text("Option C") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = optD, onValueChange = { optD = it }, label = { Text("Option D") }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(8.dp))
                Text("Correct Answer: Option ${('A' + correctIndex)}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf("A", "B", "C", "D").forEachIndexed { idx, letter ->
                        FilterChip(
                            selected = correctIndex == idx,
                            onClick = { correctIndex = idx },
                            label = { Text(letter) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = explanation,
                    onValueChange = { explanation = it },
                    label = { Text("Detailed Explanation") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val q = Question(
                        id = initialQuestion?.id ?: ("custom_" + UUID.randomUUID().toString().take(8)),
                        subject = subject.trim().lowercase(),
                        grade = gradeStr.toIntOrNull() ?: 12,
                        unitNumber = unitNumStr.toIntOrNull() ?: 1,
                        unitTitle = unitTitle.trim().ifEmpty { "Unclassified" },
                        topic = topic.trim().ifEmpty { "General" },
                        year = year,
                        questionNumber = questionNumberStr.toIntOrNull() ?: 1,
                        questionText = questionText.trim(),
                        options = listOf(optA, optB, optC, optD),
                        correctAnswer = correctIndex,
                        explanation = explanation.trim(),
                        isCustom = true
                    )
                    onSave(q)
                },
                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}
