package com.example.inventumopus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.inventumopus.datamodels.Qualification

@Composable
fun QualificationsScreen(
    navHostController: NavHostController,
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
        AddQualificationDialog(
            showDialog = showQualDialog,
            school_ = school,
            degree_ = degree,
            field_ = field,
            handleSubmit = {viewModel.addQualification(
                Qualification(
                    username = currentUser?.username!!,
                    school = school,
                    field = field,
                    degree = degree
                )
            )},
            onDismiss = { showQualDialog = false }
        )
    }
}

@Composable
fun AddQualificationDialog(
    showDialog: Boolean,
    school_: String,
    degree_: String,
    field_: String,
    handleSubmit: () -> Unit,
    onDismiss: () -> Unit,
) {
    val formLabelModifier = Modifier.padding(start = 8.dp, bottom = 5.dp)
    var school by remember { mutableStateOf(school_) }
    var degree by remember { mutableStateOf(degree_) }
    var field by remember { mutableStateOf(field_) }

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
                    Button(onClick = handleSubmit) {
                        Text(text = "Submit", fontFamily = raleway)
                    }
                }
            }
        }
    }
}