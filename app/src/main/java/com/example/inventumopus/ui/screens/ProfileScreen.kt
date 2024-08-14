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
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.ui.GoogleFonts


@Composable
fun ProfileScreen (
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    val isLoggedIn by viewModel.signedIn.collectAsState()

    Column (
        modifier = Modifier
            .padding(
                top = 60.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            )
            .fillMaxSize(),
    ) {
        if (isLoggedIn) {
            Text(
                text = "User Profile",
                fontFamily = prata,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
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
        modifier = Modifier.fillMaxSize(),
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