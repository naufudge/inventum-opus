package com.example.inventumopus.ui.screens

import InformationModal
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.datamodels.UserCreation
import com.example.inventumopus.ui.theme.Orange1

@Composable
fun SignUpScreen (
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }


    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign Up",
            fontFamily = prata,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        ) {
            // Username
            SignInFields(
                query = username,
                onQueryChange = { username = it },
                leadingIcon = Icons.Default.Person,
                fieldName = "Username"
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Email
            SignInFields(
                fieldName = "Email",
                query = email,
                onQueryChange = { email = it },
                leadingIcon = Icons.Default.Email,
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Password
            SignInFields(
                fieldName = "Password",
                query = password,
                onQueryChange = { password = it },
                leadingIcon = Icons.Default.Warning,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Password Confirmation
            SignInFields(
                fieldName = "Confirm Password",
                query = passwordConfirm,
                onQueryChange = { passwordConfirm = it },
                leadingIcon = Icons.Default.Warning,
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            modifier = Modifier
                .height(48.dp)
                .width(180.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange1),
            onClick = {
                // on sign up
                if (username == "" || email == "" || password == "" || passwordConfirm == "") {
                    dialogTitle = "Error"
                    message = "Please fill all the fields!"
                    showDialog = true
                } else {
                    if (password == passwordConfirm) {
                        viewModel.createUser(
                            UserCreation(
                                username = username,
                                email = email,
                                password = password
                            )
                        )

                        dialogTitle = "Success"
                        message = "User created successfully. You can sign in now!"
                        showDialog = true
                        navHostController.navigate("signin")
                    } else {
                        dialogTitle = "Error"
                        message = "Password does not match!"
                        showDialog = true
                    }
                }
            }
        ) {
            Text(
                text = "Sign Up",
                fontFamily = raleway,
                fontWeight = FontWeight.W500
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