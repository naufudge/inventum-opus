package com.example.inventumopus.ui.screens

import InformationModal
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.R
import com.example.inventumopus.ui.GoogleFonts
import com.example.inventumopus.ui.theme.Orange1


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
            // Image
            Column (
                modifier = Modifier.size(250.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.io_logo),
                    contentDescription = "logo"
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            // Screen Title
            Text(
                text = "Sign In",
                fontFamily = prata,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            Column(
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

                // Password
                SignInFields(
                    query = password,
                    onQueryChange = { password = it },
                    leadingIcon = Icons.Default.Warning,
                    fieldName = "Password",
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                modifier = Modifier
                    .height(48.dp)
                    .width(180.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Orange1),
                onClick = {
                    viewModel.getUser(username)
                    if (currentUser?.username == username && currentUser.password == password) {
                        viewModel.setSignInStatus(true)
                        navHostController.navigate("profile")
                    } else {
                        dialogTitle = "Login Failed!"
                        message = "Username or Password is incorrect"
                        showDialog = true
                    }
//                    } else {
//                        dialogTitle = "Login Failed!"
//                        message = "Username or Password is incorrect"
//                        showDialog = true
//                    }

                }) {
                Text(
                    text = "Sign In",
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
}

@Composable
fun SignInFields(
    query: String,
    onQueryChange: (String) -> Unit,
    leadingIcon: ImageVector,
    fieldName: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None
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
//        placeholder = { Text(text = fieldName, fontFamily = raleway, fontSize = 13.sp) },
        leadingIcon = {
            if (fieldName == "Password") {
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
        textStyle = TextStyle(fontFamily = raleway),
        label = { Text(text = fieldName, fontFamily = raleway, fontSize = 13.sp) }
    )
}