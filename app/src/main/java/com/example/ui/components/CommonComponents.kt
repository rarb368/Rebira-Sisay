package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentPink
import com.example.ui.theme.TealPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String = "3RM Ethiopian Entrance Exam",
    onMenuClick: () -> Unit,
    showShareAndTelegram: Boolean = true
) {
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.testTag("app_menu_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        },
        actions = {
            if (showShareAndTelegram) {
                // Share action
                IconButton(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Prepare for Ethiopian National Entrance Exam with 3RM Ethiopian Entrance Exam for AIBSBSS! Join our channel: https://t.me/rebiraentranceexam"
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share 3RM Exam App"))
                    },
                    modifier = Modifier.testTag("app_share_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White
                    )
                }

                // Telegram channel action
                IconButton(
                    onClick = {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/rebiraentranceexam"))
                        context.startActivity(browserIntent)
                    },
                    modifier = Modifier.testTag("telegram_channel_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Telegram Channel",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TealPrimary,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

@Composable
fun BottomNavBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.testTag("bottom_nav_bar")
    ) {
        NavigationBarItem(
            selected = selectedTab == "home",
            onClick = { onTabSelected("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("HOME", fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TealPrimary,
                selectedTextColor = TealPrimary,
                indicatorColor = TealPrimary.copy(alpha = 0.15f)
            ),
            modifier = Modifier.testTag("nav_home")
        )

        NavigationBarItem(
            selected = selectedTab == "contact",
            onClick = { onTabSelected("contact") },
            icon = { Icon(Icons.Default.MailOutline, contentDescription = "Contact Us") },
            label = { Text("CONTACT US", fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TealPrimary,
                selectedTextColor = TealPrimary,
                indicatorColor = TealPrimary.copy(alpha = 0.15f)
            ),
            modifier = Modifier.testTag("nav_contact")
        )

        NavigationBarItem(
            selected = selectedTab == "admin",
            onClick = { onTabSelected("admin") },
            icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin") },
            label = { Text("ADMIN", fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TealPrimary,
                selectedTextColor = TealPrimary,
                indicatorColor = TealPrimary.copy(alpha = 0.15f)
            ),
            modifier = Modifier.testTag("nav_admin")
        )
    }
}

@Composable
fun CircularScoreRing(
    percentage: Float, // 0.0 to 100.0
    modifier: Modifier = Modifier,
    sizeDp: Int = 96,
    strokeWidthDp: Int = 8,
    activeColor: Color = AccentPink
) {
    val sweepAngle = (percentage / 100f) * 360f

    Box(
        modifier = modifier.size(sizeDp.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding((strokeWidthDp / 2).dp)) {
            // Background track
            drawCircle(
                color = Color(0xFFE2E8F0),
                style = Stroke(width = strokeWidthDp.dp.toPx())
            )
            // Progress arc
            if (sweepAngle > 0) {
                drawArc(
                    color = activeColor,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidthDp.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }
        Text(
            text = "${percentage.toInt()}%",
            fontWeight = FontWeight.ExtraBold,
            fontSize = (sizeDp * 0.28).sp,
            color = Color(0xFF1E293B)
        )
    }
}

@Composable
fun QuitExamConfirmationDialog(
    onContinue: () -> Unit,
    onQuit: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onContinue,
        title = { Text("Quit Examination", fontWeight = FontWeight.Bold) },
        text = {
            Text("Are you sure you want to quit this exam? Your current answers and remaining time will be saved so you can resume anytime.")
        },
        confirmButton = {
            Button(
                onClick = onQuit,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.testTag("confirm_quit_button")
            ) {
                Text("Quit Exam")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onContinue,
                modifier = Modifier.testTag("continue_exam_button")
            ) {
                Text("Continue Exam")
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun ExamModeSelectionDialog(
    examTitle: String,
    onDismiss: () -> Unit,
    onSelectMode: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Reveal Answers Mode", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "When starting $examTitle, choose when correct answers and detailed explanations should be revealed:",
                    fontSize = 14.sp,
                    color = Color(0xFF475569),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Option 1: Right away
                ElevatedCard(
                    onClick = { onSelectMode("right_away") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .testTag("mode_right_away"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFF0FDF4))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF16A34A),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Right away", fontWeight = FontWeight.Bold, color = Color(0xFF14532D))
                            Text("Instant feedback, green/red highlight & explanation per question", fontSize = 12.sp, color = Color(0xFF166534))
                        }
                    }
                }

                // Option 2: After finishing
                ElevatedCard(
                    onClick = { onSelectMode("after_finishing") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("mode_after_finishing"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFEFF6FF))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("After finishing", fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A))
                            Text("Real examination simulation. All scores and explanations revealed upon submission", fontSize = 12.sp, color = Color(0xFF1D4ED8))
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}
