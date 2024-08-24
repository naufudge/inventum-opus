package com.example.inventumopus.ui.screens

import InformationModal
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.R
import com.example.inventumopus.ui.GoogleFonts


@Composable
fun SignInScreen (
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    val isLoggedIn by viewModel.signedIn.collectAsState()
    val currentUser = viewModel.currentUser

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }

    if (isLoggedIn) {
        navHostController.navigate("profile")
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign In",
                fontFamily = prata,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                // Username
                Text(
                    text = "Username",
                    fontFamily = raleway,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Left,
//                    color = Color.DarkGray
                )
                SignInFields(
                    query = username,
                    onQueryChange = { username = it },
                    leadingIcon = Icons.Default.Person,
                    placeholderText = "Username"
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Password
                Text(
                    text = "Password",
                    fontFamily = raleway,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Left,
//                    color = Color.DarkGray
                )
                SignInFields(
                    query = password,
                    onQueryChange = { password = it },
                    leadingIcon = Icons.Default.Warning,
                    placeholderText = "Password",
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Spacer(modifier = Modifier.height(25.dp))

            OutlinedButton(
                onClick = {
                    viewModel.getUser(username)
                    if (currentUser != null) {
                        if (currentUser.username == username && currentUser.password == password) {
                            viewModel.setSignInStatus(true)
                            navHostController.navigate("profile")
                        } else {
                            dialogTitle = "Login Failed!"
                            message = "Username or Password is incorrect"
                            showDialog = true
                        }
                    }
                }) {
                Text(
                    text = "Sign In",
                    fontFamily = poppins,
//                    color = Color.DarkGray
                )
            }
            InformationModal(
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                message = message,
                title = dialogTitle
            )
        }
    }
}

@Composable
fun SignInFields(
    query: String,
    onQueryChange: (String) -> Unit,
    leadingIcon: ImageVector,
    placeholderText: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(query) }

    val font = GoogleFonts()
    val raleway = font.Raleway

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onQueryChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        placeholder = { Text(text = placeholderText, fontFamily = raleway, fontSize = 13.sp) },
        leadingIcon = {
            if (placeholderText == "Password") {
                Icon(painter = painterResource(R.drawable.password), contentDescription = "Password")
            } else {
                Icon(imageVector = leadingIcon, contentDescription = "Sign in/up field")
            }
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { text = ""; onQueryChange("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear Icon")
                }
            }
        },
        singleLine = true,
        visualTransformation = visualTransformation,
        textStyle = TextStyle(fontFamily = raleway)
    )
}