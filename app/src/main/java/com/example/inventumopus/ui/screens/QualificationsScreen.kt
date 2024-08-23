package com.example.inventumopus.ui.screens

import InformationModal
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.datamodels.Qualification

@Composable
fun QualificationsScreen(
    viewModel: HomeViewModel
) {
    val currentUser = viewModel.currentUser

    var showQualDialog by remember { mutableStateOf(false) }

    var school by remember { mutableStateOf("") }
    var degree by remember { mutableStateOf("") }
    var field by remember { mutableStateOf("") }

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
                text = "Qualifications",
                fontFamily = prata,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            IconButton(
                onClick = {
                    showQualDialog = true
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add_experience")
            }
        }

        // Qualifications list
        LazyColumn (
            modifier = Modifier
                .padding(top = 35.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(currentUser?.qualifications!!) { _, qualification ->
                // Each qualification card
                ElevatedCard (
                    modifier = Modifier
                        .height(100.dp)
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
                                        text = "${qualification.degree.first()}",
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = poppins,
                                        fontSize = 23.sp
                                    )
                                }

                            }
                        }
                        // Qualification Details Column
                        Column (
                            modifier = Modifier.padding(horizontal = 40.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = qualification.degree,
                                fontFamily = poppins,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = qualification.field, fontFamily = poppins, fontSize = 14.sp)
                            Text(text = qualification.school, fontFamily = poppins, fontSize = 14.sp, fontStyle = FontStyle.Italic)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }
        }

        AddQualificationDialog(
            showDialog = showQualDialog,
            viewModel = viewModel,
            onDismiss = { showQualDialog = false }
        )
    }
}

@Composable
fun AddQualificationDialog(
    showDialog: Boolean,
    viewModel: HomeViewModel,
    onDismiss: () -> Unit
) {
    var school by remember { mutableStateOf("") }
    var degree by remember { mutableStateOf("") }
    var field by remember { mutableStateOf("") }

    var infoDialogTitle by remember { mutableStateOf("") }
    var infoDialogMsg by remember { mutableStateOf("") }
    var showInfoDialog by remember { mutableStateOf(false) }

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
                        text = "Add Qualification",
                        fontFamily = poppins,
                        fontWeight = FontWeight.W700,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    // Name of School Field
                    val name = "Name of School"
                    Column {
                        OutlinedTextField(
                            value = school,
                            onValueChange = { school = it },
                            label = { Text(text = name, fontFamily = raleway) }
                        )
                    }

                    // Field of Education Field
                    Spacer(modifier = Modifier.height(25.dp))
                    Column {
                        OutlinedTextField(
                            value = field,
                            onValueChange = { field = it },
                            label = { Text(text = "Field of Education", fontFamily = raleway) }
                        )
                    }

                    // Degree of Qualification Field
                    Spacer(modifier = Modifier.height(25.dp))
                    Column {
                        OutlinedTextField(
                            value = degree,
                            onValueChange = { degree = it },
                            label = { Text(text = "Degree of Qualification", fontFamily = raleway) }
                        )
                    }

                    Spacer(modifier = Modifier.height(25.dp))
                    Button(onClick = {
                        if (school != "" && field != "" && degree != "") {
                            viewModel.addQualification(
                                Qualification(
                                    username = viewModel.currentUser?.username!!,
                                    school = school,
                                    field = field,
                                    degree = degree
                                )
                            )
                            school = ""
                            field = ""
                            degree = ""

                            infoDialogTitle = "Success"
                            infoDialogMsg = "Successfully added qualification!"
                            showInfoDialog = true
                        } else {
                            infoDialogTitle = "Error"
                            infoDialogMsg = "Please fill all the fields!"
                            showInfoDialog = true
                        }
                    }) {
                        Text(text = "Submit", fontFamily = raleway)
                    }
                }
            }
        }
        InformationModal(
            showDialog = showInfoDialog,
            onDismiss = { showInfoDialog = false },
            message = infoDialogMsg,
            title = infoDialogTitle
        )
    }
}