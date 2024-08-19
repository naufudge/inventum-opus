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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.inventumopus.HomeViewModel


@Composable
fun ProfileScreen (
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    val isLoggedIn by viewModel.signedIn.collectAsState()
    val currentUser = viewModel.currentUser
    
    var showExpDialog by remember { mutableStateOf(false) }
    var showQualDialog by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .padding(
                top = 30.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            )
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        if (isLoggedIn) {
//            Text(
//                text = "User Profile",
//                fontFamily = prata,
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//            )
            
            // Settings button
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                }
            }
            
            // Profile Pic
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(200.dp),
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data("https://i.pinimg.com/736x/81/1b/d2/811bd2ba1518d33349ea863931da7e9f.jpg")
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = null
                )
            }

            Column {
                // Username
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text (
                        text= currentUser?.username!!,
                        fontFamily = poppins,
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }

                // Can make this a lazy row of cards?...
                // Experience
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp)
                ) {
                    Column (modifier = Modifier.padding(10.dp)) {
                        Row (
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp, vertical = 4.dp),
                                text = "Past Experiences",
                                fontFamily = poppins,
                                fontWeight = FontWeight.W700,
                                color = Color.DarkGray
                            )
                            IconButton(onClick = { 
                                showExpDialog = true
                            }) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Experience")
                            }
                        }
                        
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = "Demon Slaying",
                            color = Color.Gray,
                        )
                    }
                }
                
                // Qualifications
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp)
                ) {
                    Column (modifier = Modifier.padding(10.dp)) {
                        Row (
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                                text = "Education",
                                fontFamily = poppins,
                                fontWeight = FontWeight.W700,
                                color = Color.DarkGray
                            )
                            IconButton(onClick = {
                                showQualDialog = true
                            }) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Experience")
                            }
                        }
                        
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = "Thunder Breathing First Form",
                            color = Color.Gray,
                        )
                    }
                }
            }
            AddExperienceDialog(showDialog = showExpDialog, onDismiss = { showExpDialog = false })
            AddQualificationDialog(showDialog = showQualDialog, onDismiss = { showQualDialog = false })
        } else {
            LoggedOutScreen(navHostController)
        }
    }
}

@Composable
fun LoggedOutScreen(
    navHostController: NavHostController
) {
    Column (
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Please Sign In!",
            fontFamily = prata,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Please sign in or sign up to view your profile and apply for your dream job!",
            fontFamily = poppins,
            fontSize = 13.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
        Row (
            modifier = Modifier.fillMaxWidth(0.7f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Sign in button
            OutlinedButton(
                onClick = {
                    navHostController.navigate("signin")
                }
            ) {
                Text(
                    text = "Sign In",
                    fontFamily = poppins,
                    color = Color.DarkGray
                )
            }

            // Sign up button
            OutlinedButton (
                onClick = {
                    navHostController.navigate("signup")
                }
            ) {
                Text(
                    text = "Sign Up",
                    fontFamily = poppins,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun AddExperienceDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
) {
    var jobTitle by remember { mutableStateOf("") }
    var jobCompany by remember { mutableStateOf("") }
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

@Composable
fun AddQualificationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
) {
    var school by remember { mutableStateOf("") }
    var degree by remember { mutableStateOf("") }
    var field by remember { mutableStateOf("") }

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
                        // submit qualification

                    }) {
                        Text(text = "Submit", fontFamily = raleway)
                    }
                }
            }
        }
    }
}