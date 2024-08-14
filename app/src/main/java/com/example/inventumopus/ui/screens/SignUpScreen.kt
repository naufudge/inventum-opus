package com.example.inventumopus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel

@Composable
fun SignUpScreen (
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign Up",
            fontFamily = prata,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Column (
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
                color = Color.DarkGray
            )
            SignInFields(
                query = "",
                onQueryChange = {},
                leadingIcon = Icons.Default.Person,
                placeholderText = "Username"
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Email
            Text(
                text = "Email",
                fontFamily = raleway,
                fontSize = 13.sp,
                textAlign = TextAlign.Left,
                color = Color.DarkGray
            )
            SignInFields(
                query = "",
                onQueryChange = {},
                leadingIcon = Icons.Default.Email,
                placeholderText = "Email"
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Password
            Text(
                text = "Password",
                fontFamily = raleway,
                fontSize = 13.sp,
                textAlign = TextAlign.Left,
                color = Color.DarkGray
            )
            SignInFields(
                query = "",
                onQueryChange = {},
                leadingIcon = Icons.Default.Warning,
                placeholderText = "Password",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Password Confirmation
            Text(
                text = "Confirm Password",
                fontFamily = raleway,
                fontSize = 13.sp,
                textAlign = TextAlign.Left,
                color = Color.DarkGray
            )
            SignInFields(
                query = "",
                onQueryChange = {},
                leadingIcon = Icons.Default.Warning,
                placeholderText = "Confirm Password",
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedButton(onClick = { /*TODO*/ }) {
            Text(
                text = "Sign Up",
                fontFamily = poppins,
                color = Color.DarkGray
            )
        }
    }
}