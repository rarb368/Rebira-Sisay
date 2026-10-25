package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ExamSubject

@Composable
fun SubjectGridCard(
    subject: ExamSubject,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("subject_card_${subject.id}"),
        colors = CardDefaults.cardColors(containerColor = Color(subject.hexColor)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Main colored body
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Title
                Text(
                    text = subject.title,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp,
                    letterSpacing = 0.5.sp
                )

                // Visual Artwork for Subject matching Ethio Matric screenshots
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SubjectGraphicIcons(subject = subject)
                }
            }

            // White bottom accent bar (matching Ethio Matric screenshot)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(Color.White)
            )
        }
    }
}

@Composable
private fun SubjectGraphicIcons(subject: ExamSubject) {
    when (subject) {
        ExamSubject.BIOLOGY -> {
            // Microscope & DNA
            Icon(
                imageVector = Icons.Default.Biotech,
                contentDescription = "Microscope",
                tint = Color.White.copy(alpha = 0.95f),
                modifier = Modifier.size(54.dp)
            )
            Icon(
                imageVector = Icons.Default.Coronavirus,
                contentDescription = "DNA / Living Organisms",
                tint = Color.White.copy(alpha = 0.95f),
                modifier = Modifier.size(46.dp)
            )
        }
        ExamSubject.CHEMISTRY -> {
            // Test tube & Flask
            Icon(
                imageVector = Icons.Default.Science,
                contentDescription = "Flask",
                tint = Color.White.copy(alpha = 0.95f),
                modifier = Modifier.size(52.dp)
            )
            Icon(
                imageVector = Icons.Default.WaterDrop,
                contentDescription = "Solutions",
                tint = Color.White.copy(alpha = 0.95f),
                modifier = Modifier.size(44.dp)
            )
        }
        ExamSubject.PHYSICS -> {
            // Magnet & Glowing Bulb
            Icon(
                imageVector = Icons.Default.FlashOn,
                contentDescription = "Electricity / Magnetism",
                tint = Color.White.copy(alpha = 0.95f),
                modifier = Modifier.size(50.dp)
            )
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = "Lightbulb",
                tint = Color(0xFFFFEB3B),
                modifier = Modifier.size(48.dp)
            )
        }
        ExamSubject.MATHS_NAT -> {
            // [x² + x] box & Geometry Triangle
            Box(
                modifier = Modifier
                    .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "x² + x",
                    color = Color(0xFF4ADE80),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
            Icon(
                imageVector = Icons.Default.ChangeHistory,
                contentDescription = "Triangle Geometry",
                tint = Color(0xFF67E8F9),
                modifier = Modifier.size(52.dp)
            )
        }
        ExamSubject.MATHS_SOC -> {
            // Bar Chart with trend & [log₂8]
            Icon(
                imageVector = Icons.Default.BarChart,
                contentDescription = "Statistics Graph",
                tint = Color(0xFF38BDF8),
                modifier = Modifier.size(50.dp)
            )
            Box(
                modifier = Modifier
                    .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "log₂8",
                    color = Color(0xFFFDE047),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
        ExamSubject.ENGLISH -> {
            // Megaphone & Speech Bubble
            Icon(
                imageVector = Icons.Default.Campaign,
                contentDescription = "Megaphone",
                tint = Color.White.copy(alpha = 0.95f),
                modifier = Modifier.size(52.dp)
            )
            Icon(
                imageVector = Icons.Default.Forum,
                contentDescription = "Speech Bubbles",
                tint = Color.White.copy(alpha = 0.95f),
                modifier = Modifier.size(46.dp)
            )
        }
        ExamSubject.SAT -> {
            // Speech bubbles & [π] badge
            Icon(
                imageVector = Icons.Default.ChatBubble,
                contentDescription = "Reasoning",
                tint = Color.White.copy(alpha = 0.95f),
                modifier = Modifier.size(48.dp)
            )
            Box(
                modifier = Modifier
                    .background(Color(0xFF334155), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "π",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}
