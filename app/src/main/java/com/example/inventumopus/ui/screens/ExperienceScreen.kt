package com.example.inventumopus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel

@Composable
fun ExperienceScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    var showExpDialog by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .padding(
                top = 40.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            )
            .fillMaxSize(),
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Past Experiences",
                fontFamily = prata,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            IconButton(
                onClick = {
                    showExpDialog = true
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add_experience")
            }
        }
        AddExperienceDialog(showDialog = showExpDialog, onDismiss = { showExpDialog = false })
    }

}

@Composable
fun AddExperienceDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
) {
    var jobTitle by remember { mutableStateOf("") }
    var jobCompany by remember { mutableStateOf("") }
    var jobYears by remember { mutableStateOf("") }
    var jobResponsibilities by remember { mutableStateOf("") }

    val formLabelModifier = Modifier.padding(start = 8.dp, bottom = 5.dp)

    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Surface (
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 8.dp
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 30.dp, end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row (
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                        }
                    }
                    Text(
                        text = "Add Experience",
                        fontFamily = poppins,
                        fontWeight = FontWeight.W700,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(25.dp))
                    // Job Title Field
                    Column {
                        OutlinedTextField(
                            value = jobTitle,
                            onValueChange = { jobTitle = it },
                            label = { Text(text = "Job Title", fontFamily = raleway) }
                        )
                    }

                    // Company Name Field
                    Spacer(modifier = Modifier.height(25.dp))
                    val company = "Company Name"
                    Column {
                        OutlinedTextField(
                            value = jobCompany,
                            onValueChange = { jobCompany = it },
                            label = { Text(text = company, fontFamily = raleway) }
                        )
                    }

                    // Number of Years Field
                    Spacer(modifier = Modifier.height(25.dp))
                    Column {
                        OutlinedTextField(
                            value = jobCompany,
                            onValueChange = { jobCompany = it },
                            label = { Text(text = "Number of Years Worked", fontFamily = raleway) }
                        )
                    }

                    // Responsibilities
                    Spacer(modifier = Modifier.height(25.dp))
                    val responsibility = "Responsibilities"
                    Column {
                        OutlinedTextField(
                            value = jobResponsibilities,
                            onValueChange = { jobResponsibilities = it },
                            label = { Text(text = responsibility, fontFamily = raleway) }
                        )
                    }

                    Spacer(modifier = Modifier.height(25.dp))
                    Button(onClick = {
                        // submit experience

                    }) {
                        Text(text = "Submit", fontFamily = raleway)
                    }
                }
            }
        }
    }
}