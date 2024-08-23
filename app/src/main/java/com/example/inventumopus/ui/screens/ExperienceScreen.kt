package com.example.inventumopus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.datamodels.Experience

@Composable
fun ExperienceScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    val currentUser = viewModel.currentUser
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
        // Experience list
        LazyColumn (
            modifier = Modifier
                .padding(top = 35.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(currentUser?.experience!!) { _, experience ->
                // Each Experience card
                ElevatedCard (
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                ) {
                    Row (
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icon Column
                        Column (
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,

                            ) {
                            Card (
                                modifier = Modifier
                                    .size(65.dp),
                                colors = CardColors(
                                    containerColor = Color.Gray,
                                    contentColor = Color.White,
                                    disabledContentColor = Color.DarkGray,
                                    disabledContainerColor = Color.LightGray
                                )
                            ) {
                                Column (
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "${experience.companyName.first()}",
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = poppins,
                                        fontSize = 23.sp
                                    )
                                }

                            }
                        }
                        // Experience Details Column
                        Column (
                            modifier = Modifier.padding(horizontal = 40.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = experience.jobTitle,
                                fontFamily = poppins,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = experience.companyName, fontFamily = poppins, fontSize = 14.sp, fontStyle = FontStyle.Italic)
                            Text(text = "${experience.years} Years", fontFamily = poppins, fontSize = 14.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }
        }

        AddExperienceDialog(
            showDialog = showExpDialog,
            viewModel = viewModel,
            onDismiss = { showExpDialog = false })
    }

}

@Composable
fun AddExperienceDialog(
    showDialog: Boolean,
    viewModel: HomeViewModel,
    onDismiss: () -> Unit,
) {
    val user = viewModel.currentUser
    var jobTitle by remember { mutableStateOf("") }
    var jobCompany by remember { mutableStateOf("") }
    var jobYears by remember { mutableStateOf("") }
    var jobResponsibilities by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }

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
                    Column {
                        OutlinedTextField(
                            value = jobCompany,
                            onValueChange = { jobCompany = it },
                            label = { Text(text = "Company Name", fontFamily = raleway) }
                        )
                    }

                    // Number of Years Field
                    Spacer(modifier = Modifier.height(25.dp))
                    Column {
                        OutlinedTextField(
                            value = jobYears,
                            onValueChange = {
                                jobYears = it
                                isError = !it.all { char -> char.isDigit() }
                            },
                            label = { Text(text = "Number of Years Worked", fontFamily = raleway) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = isError
                        )
                    }

                    // Responsibilities
                    Spacer(modifier = Modifier.height(25.dp))
                    Column {
                        OutlinedTextField(
                            value = jobResponsibilities,
                            onValueChange = { jobResponsibilities = it },
                            label = { Text(text = "Responsibilities", fontFamily = raleway) }
                        )
                    }

                    Spacer(modifier = Modifier.height(25.dp))
                    Button(onClick = {
                        // submit experience
                        if (jobTitle != "" && jobCompany != "" && jobYears != "" && jobResponsibilities != "") {
                            viewModel.addExperience(Experience(
                                username = user?.username!!,
                                jobTitle = jobTitle,
                                companyName = jobCompany,
                                years = jobYears.toInt(),
                                responsibilities = jobResponsibilities
                            ))
                        }
                    }) {
                        Text(text = "Submit", fontFamily = raleway)
                    }
                }
            }
        }
    }
}