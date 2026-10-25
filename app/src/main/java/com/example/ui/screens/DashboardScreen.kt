package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ActiveExamSession
import com.example.model.ExamSubject
import com.example.model.StudentProfile
import com.example.ui.components.AppTopBar
import com.example.ui.components.BottomNavBar
import com.example.ui.components.SubjectGridCard
import com.example.ui.theme.TealPrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    studentProfile: StudentProfile,
    activeSession: ActiveExamSession?,
    totalCachedQuestions: Int = 380,
    onSubjectClick: (ExamSubject) -> Unit,
    onResumeSession: () -> Unit,
    onDiscardSession: () -> Unit,
    onNavigateBottom: (String) -> Unit
) {
    val context = LocalContext.current
    var showProfileDialog by remember { mutableStateOf(false) }
    var selectedGradeYearText by remember { mutableStateOf("2014/15 - 2017") }
    var showYearDropdown by remember { mutableStateOf(false) }

    val yearOptions = listOf(
        "2014/15 - 2017 (All Available)",
        "2017 July E.C. (Latest)",
        "2016 July E.C.",
        "2015 July E.C.",
        "2014/15 Special E.C."
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "3RM Ethiopian Entrance Exam",
                onMenuClick = { showProfileDialog = true },
                showShareAndTelegram = true
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedTab = "home",
                onTabSelected = onNavigateBottom
            )
        },
        containerColor = Color(0xFFF1F5F9)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Sub-header examination-year selector bar matching screenshot
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showYearDropdown = true },
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Grade 9 to 12:",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF475569),
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = selectedGradeYearText,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF0F172A),
                            fontSize = 15.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select Year",
                        tint = Color(0xFF475569)
                    )

                    DropdownMenu(
                        expanded = showYearDropdown,
                        onDismissRequest = { showYearDropdown = false }
                    ) {
                        yearOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt, fontSize = 14.sp) },
                                onClick = {
                                    selectedGradeYearText = opt.substringBefore(" (")
                                    showYearDropdown = false
                                }
                            )
                        }
                    }
                }
            }

            // Service Worker & Offline Cache Status Indicator
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA5D6A7))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudDone,
                        contentDescription = "Offline Cache Ready",
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Offline Mode Active • Service Worker Cached $totalCachedQuestions Questions",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1B5E20)
                    )
                }
            }

            // Resume active session banner if student previously quit without submitting
            if (activeSession != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .testTag("resume_exam_banner"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Unfinished Exam in Progress",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E),
                                fontSize = 14.sp
                            )
                            Text(
                                text = "${activeSession.subject.uppercase()} (${activeSession.year}) • ${activeSession.answers.size} answered",
                                fontSize = 12.sp,
                                color = Color(0xFFB45309)
                            )
                        }

                        Row {
                            TextButton(onClick = onDiscardSession) {
                                Text("Discard", color = Color(0xFFB45309), fontSize = 12.sp)
                            }
                            Button(
                                onClick = onResumeSession,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("resume_exam_button")
                            ) {
                                Text("Resume", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Grid of 7 Subject Cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .testTag("subjects_grid"),
                contentPadding = PaddingValues(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ExamSubject.entries) { subject ->
                    SubjectGridCard(
                        subject = subject,
                        onClick = { onSubjectClick(subject) }
                    )
                }
            }
        }
    }

    // Student Profile Dialog
    if (showProfileDialog) {
        AlertDialog(
            onDismissRequest = { showProfileDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = TealPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Student Profile", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ProfileItemRow("Full Name", studentProfile.fullName)
                    ProfileItemRow("Phone Number", studentProfile.phone)
                    ProfileItemRow("Gmail Address", studentProfile.email)
                    ProfileItemRow("School", "Ambo Ifa Boru Special Boarding Secondary School")
                    ProfileItemRow(
                        "Registered On",
                        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US).format(Date(studentProfile.registeredAt))
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "3RM Ethiopian Entrance Exam Platform",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                    Text(
                        text = "Developer: REBIRA SISAY GITI (3RM)",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showProfileDialog = false }) {
                    Text("Close")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun ProfileItemRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, fontSize = 11.sp, color = Color(0xFF64748B))
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))
    }
}
