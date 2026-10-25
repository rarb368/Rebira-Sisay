package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.StudentProfile
import com.example.ui.theme.TealPrimary
import java.util.UUID

@Composable
fun RegistrationScreen(
    onRegistered: (StudentProfile) -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var fullNameError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    fun validateAndSubmit() {
        var isValid = true

        if (fullName.trim().length < 3) {
            fullNameError = "Please enter your full name (minimum 3 characters)"
            isValid = false
        } else {
            fullNameError = null
        }

        val cleanedPhone = phone.trim().replace(" ", "").replace("-", "")
        if (cleanedPhone.length < 9 || !cleanedPhone.all { it.isDigit() || it == '+' }) {
            phoneError = "Please enter a valid phone number (e.g. 0912345678 or +251...)"
            isValid = false
        } else {
            phoneError = null
        }

        val trimmedEmail = email.trim()
        if (!trimmedEmail.contains("@") || !trimmedEmail.contains(".") || trimmedEmail.length < 5) {
            emailError = "Please enter a valid Gmail address (e.g. student@gmail.com)"
            isValid = false
        } else {
            emailError = null
        }

        if (isValid) {
            val profile = StudentProfile(
                id = "std_" + UUID.randomUUID().toString().take(8),
                fullName = fullName.trim(),
                phone = cleanedPhone,
                email = trimmedEmail,
                registeredAt = System.currentTimeMillis()
            )
            onRegistered(profile)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // App Emblem Banner
            Surface(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(20.dp)),
                color = TealPrimary,
                shadowElevation = 6.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Logo",
                        tint = Color.White,
                        modifier = Modifier.size(54.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "The 3RM Ethiopian Entrance Exam for AIBSBSS",
                fontWeight = FontWeight.Black,
                fontSize = 21.sp,
                color = Color(0xFF020617),
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Ambo Ifa Boru Special Boarding Secondary School",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF004D40),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Developer: REBIRA SISAY GITI (3RM) • rarb368@gmail.com",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF334155),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFCBD5E1)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Student Registration",
                        fontWeight = FontWeight.Black,
                        fontSize = 19.sp,
                        color = Color(0xFF020617)
                    )
                    Text(
                        text = "Prepare for the Ethiopian University Entrance Examination (EUEE) with authentic questions and curriculum classification.",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Color(0xFF1E293B),
                        modifier = Modifier.padding(top = 6.dp, bottom = 20.dp)
                    )

                    val fieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TealPrimary,
                        unfocusedBorderColor = Color(0xFF475569),
                        focusedLabelColor = TealPrimary,
                        unfocusedLabelColor = Color(0xFF0F172A),
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A),
                        focusedLeadingIconColor = TealPrimary,
                        unfocusedLeadingIconColor = Color(0xFF334155)
                    )

                    // Full Name
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it; fullNameError = null },
                        label = { Text("Full Name", fontWeight = FontWeight.Bold, color = Color(0xFF0F172A)) },
                        textStyle = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF0F172A)),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        colors = fieldColors,
                        isError = fullNameError != null,
                        supportingText = fullNameError?.let { { Text(it, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error) } },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("registration_full_name_input")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Phone Number
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it; phoneError = null },
                        label = { Text("Phone Number", fontWeight = FontWeight.Bold, color = Color(0xFF0F172A)) },
                        textStyle = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF0F172A)),
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        colors = fieldColors,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        isError = phoneError != null,
                        supportingText = phoneError?.let { { Text(it, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error) } },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("registration_phone_input")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Gmail Address
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; emailError = null },
                        label = { Text("Gmail Address", fontWeight = FontWeight.Bold, color = Color(0xFF0F172A)) },
                        textStyle = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF0F172A)),
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        colors = fieldColors,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = emailError != null,
                        supportingText = emailError?.let { { Text(it, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error) } },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("registration_email_input")
                    )

                    Spacer(modifier = Modifier.height(26.dp))

                    // Register and Continue Button
                    Button(
                        onClick = { validateAndSubmit() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("register_and_continue_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "REGISTER AND CONTINUE",
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
