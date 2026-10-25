package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ExamAttempt
import com.example.ui.components.CircularScoreRing
import com.example.ui.theme.AccentPink
import com.example.ui.theme.TealPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamResultScreen(
    attempt: ExamAttempt,
    onReviewAnswers: () -> Unit,
    onTryAgain: () -> Unit,
    onReturnHome: () -> Unit
) {
    val score = attempt.scorePercentage.toFloat()
    val isPassed = score >= 50f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Examination Results", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onReturnHome) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Home",
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main Score Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${attempt.subject.uppercase()} — ${attempt.year}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color(0xFF0F172A)
                    )
                    if (attempt.isUnitPractice) {
                        Text(
                            text = "Unit Practice: ${attempt.unitTitle}",
                            fontSize = 13.sp,
                            color = TealPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Circular Score Ring
                    CircularScoreRing(
                        percentage = score,
                        sizeDp = 120,
                        strokeWidthDp = 10,
                        activeColor = if (score >= 70f) Color(0xFF16A34A) else if (score >= 50f) AccentPink else Color(0xFFDC2626)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Performance summary pill
                    Surface(
                        color = if (score >= 75f) Color(0xFFDCFCE7) else if (score >= 50f) Color(0xFFFEF3C7) else Color(0xFFFEE2E2),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = when {
                                score >= 85f -> "Excellent! Outstanding Mastery"
                                score >= 70f -> "Great Job! Well Prepared"
                                score >= 50f -> "Pass: Good Effort, Review Weak Units"
                                else -> "Needs Practice: Recommend Reviewing Units"
                            },
                            color = when {
                                score >= 75f -> Color(0xFF166534)
                                score >= 50f -> Color(0xFF92400E)
                                else -> Color(0xFF991B1B)
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Detailed Breakdown Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Performance Breakdown",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF0F172A),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    ResultItemRow("Student Name", attempt.studentName)
                    ResultItemRow("Total Questions", "${attempt.totalQuestions}")
                    ResultItemRow("Correct Answers", "${attempt.correctCount}", valueColor = Color(0xFF16A34A))
                    ResultItemRow("Incorrect Answers", "${attempt.incorrectCount}", valueColor = Color(0xFFDC2626))
                    ResultItemRow("Unanswered", "${attempt.unansweredCount}", valueColor = Color(0xFFD97706))
                    ResultItemRow("Final Normalized Score", "%.1f%%".format(attempt.scorePercentage))
                    ResultItemRow("Time Spent", formatTime(attempt.timeTakenSeconds))
                    if (attempt.timeRemainingSeconds > 0) {
                        ResultItemRow("Time Remaining", formatTime(attempt.timeRemainingSeconds))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            Button(
                onClick = onReviewAnswers,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("review_answers_button"),
                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.FindInPage, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Review All Answers & Explanations", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onTryAgain,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("try_again_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Try Again", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onReturnHome,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("return_home_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF334155)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Return HOME", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ResultItemRow(label: String, value: String, valueColor: Color = Color(0xFF0F172A)) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 13.sp, color = Color(0xFF64748B))
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = valueColor)
    }
}

private fun formatTime(seconds: Long): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return "${mins}m ${secs}s"
}
