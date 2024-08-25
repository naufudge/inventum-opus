package com.example.inventumopus.ui.screens

import InformationModal
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.datamodels.Experience
import com.example.inventumopus.datamodels.Qualification
import com.example.inventumopus.ui.components.Loading
import com.example.inventumopus.ui.theme.Orange1
import kotlin.math.exp

@Composable
fun QualificationsScreen(
    viewModel: HomeViewModel
) {
    val currentUser = viewModel.currentUser
    var qualificationScreenLoading by viewModel::qualificationScreenLoading

    var showQualDialog by remember { mutableStateOf(false) }

    var showEditOrDeleteDialog by remember { mutableStateOf(false) }
    var selectedQualification = viewModel.selectedQualification

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
        if (!qualificationScreenLoading) {
            if (currentUser?.qualifications!!.isEmpty()) {
                Column (
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "You have not added any qualifications. Please press the \"+\" button to add yours now!",
                        fontFamily = poppins,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                // Qualifications list
                LazyColumn (
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(currentUser.qualifications) { _, qualification ->
                        // Each qualification card
                        ElevatedCard (
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.selectedQualification(qualification)
                                    showEditOrDeleteDialog = true
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                        ) {
                            Row (
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Icon Column (left side of the card)
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
                                                text = "${qualification.degree!!.first()}",
                                                textAlign = TextAlign.Center,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = poppins,
                                                fontSize = 23.sp
                                            )
                                        }

                                    }
                                }
                                // Qualification Details Column (right side of the card)
                                Column (
                                    modifier = Modifier.padding(horizontal = 40.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = qualification.degree!!,
                                        fontFamily = poppins,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(text = qualification.field!!, fontFamily = poppins, fontSize = 14.sp)
                                    Text(text = qualification.school!!, fontFamily = poppins, fontSize = 14.sp, fontStyle = FontStyle.Italic)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(28.dp))
                    }
                }
            }
        } else {
            Loading()
        }

        AddQualificationDialog(
            showDialog = showQualDialog,
            viewModel = viewModel,
            onDismiss = { showQualDialog = false }
        )
        EditOrDeletePopUp(
            showDialog = showEditOrDeleteDialog,
            onDismiss = { showEditOrDeleteDialog = false },
            viewModel = viewModel,
            qualification = selectedQualification
        )
    }
}

@Composable
fun AddQualificationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    viewModel: HomeViewModel
) {
    var school by remember { mutableStateOf("") }
    var degree by remember { mutableStateOf("") }
    var field by remember { mutableStateOf("") }

    var infoDialogTitle by remember { mutableStateOf("") }
    var infoDialogMsg by remember { mutableStateOf("") }
    var showInfoDialog by remember { mutableStateOf(false) }

    var qualificationScreenLoading by viewModel::qualificationScreenLoading

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
                    Column {
                        OutlinedTextField(
                            value = school,
                            onValueChange = { school = it },
                            label = { Text(text = "Name of School", fontFamily = raleway) }
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
                    // Submit qualification button
                    Button(onClick = {
                        if (school != "" && field != "" && degree != "") {
                            viewModel.manageQualification(
                                Qualification(
                                    username = viewModel.currentUser?.username!!,
                                    school = school,
                                    field = field,
                                    degree = degree,
                                    add = true
                                )
                            )
                            qualificationScreenLoading = true

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
            onDismiss = {
                showInfoDialog = false
                onDismiss()
            },
            title = infoDialogTitle,
            message = infoDialogMsg
        )
    }
}

@Composable
fun EditOrDeletePopUp(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    viewModel: HomeViewModel,
    qualification: Qualification? = null,
    experience: Experience? = null
) {
    val currentUser = viewModel.currentUser

    var showQualificationEditDialog by remember { mutableStateOf(false) }
    var showExperienceEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 8.dp
            ) {

                Column (
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp, bottom = 25.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row (
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                        }
                    }
                    Text(
                        text = "Would you like to edit or delete the item?",
                        fontFamily = poppins,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
                    ) {
                        // Edit Button
                        OutlinedButton(
                            onClick = {
                                // Handle edit button click
                                if (qualification != null) {
                                    showQualificationEditDialog = true
                                } else {
                                    showExperienceEditDialog = true
                                }
                                onDismiss()
                            }
                        ) {
                            Icon(
                                modifier = Modifier.padding(end = 8.dp),
                                imageVector = Icons.Default.Edit,
                                contentDescription = "edit"
                            )
                            Text(text = "Edit", fontFamily = raleway, fontSize = 14.sp)
                        }

                        // Delete Button
                        Button(
                            onClick = {
                                // Handle delete button click
                                showDeleteDialog = true

                            }
                        ) {
                            Icon(
                                modifier = Modifier.padding(end = 8.dp),
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete"
                            )
                            Text(text = "Delete", fontFamily = raleway, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }

    DeleteConfirmationModal(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onDelete = {
            if (qualification != null) {
                viewModel.manageQualification(
                    Qualification(
                        username = currentUser?.username!!,
                        qualificationId = qualification.qualificationId,
                        add = false
                    )
                )
            } else {
                viewModel.manageExperience(
                    Experience(
                        username = currentUser?.username!!,
                        expId = experience?.expId,
                        add = false
                    )
                )
            }
            onDismiss()
        },
        viewModel = viewModel
    )

    if (qualification != null) {
        // Qualification editing dialog
        EditQualificationDialog(
            showDialog = showQualificationEditDialog,
            onDismiss = { showQualificationEditDialog = false },
            viewModel = viewModel,
            qualification = qualification
        )
    } else if (experience != null) {
        // Experience editing dialog
        EditExperienceDialog(
            showDialog = showExperienceEditDialog,
            onDismiss = { showExperienceEditDialog = false },
            viewModel = viewModel,
            experience = experience
        )
    }
}

@Composable
fun EditQualificationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    viewModel: HomeViewModel,
    qualification: Qualification
) {
    var school by remember { mutableStateOf(qualification.school!!) }
    var degree by remember { mutableStateOf(qualification.degree!!) }
    var field by remember { mutableStateOf(qualification.field!!) }

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
                        text = "Edit Qualification",
                        fontFamily = poppins,
                        fontWeight = FontWeight.W700,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    // Name of School Field
                    Column {
                        OutlinedTextField(
                            value = school,
                            onValueChange = { school = it },
                            label = { Text(text = "Name of School", fontFamily = raleway) }
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
                    // Edit qualification button
                    Button(onClick = {
                        if (school != "" && field != "" && degree != "") {
                            viewModel.editQualification(
                                Qualification(
                                    username = viewModel.currentUser?.username!!,
                                    qualificationId = qualification.qualificationId,
                                    school = school,
                                    field = field,
                                    degree = degree,
                                )
                            )

                            infoDialogTitle = "Success"
                            infoDialogMsg = "Successfully edited qualification!"
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
            onDismiss = {
                showInfoDialog = false
                onDismiss()
            },
            message = infoDialogMsg,
            title = infoDialogTitle
        )
    }
}

@Composable
fun DeleteConfirmationModal(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    viewModel: HomeViewModel
) {
    var qualificationScreenLoading by viewModel::qualificationScreenLoading

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(
                        text = "Are you sure?",
                        fontFamily = prata,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Are you sure that you want to delete this item?",
                        fontFamily = raleway,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row (
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // No button
                        OutlinedButton(
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(2.dp, Color.LightGray),
                            onClick = onDismiss
                        ) {
                            Text(
                                text = "No",
                                fontFamily = raleway,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        // Yes button
                        Button(
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonColors(
                                containerColor = Orange1,
                                contentColor = Color.Gray,
                                disabledContentColor = Color.White,
                                disabledContainerColor = Color.Gray
                            ),
                            onClick = {
                                onDelete()
                                qualificationScreenLoading = true
                                onDismiss()
                            }
                        ) {
                            Text(
                                text = "Yes",
                                fontFamily = raleway,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}