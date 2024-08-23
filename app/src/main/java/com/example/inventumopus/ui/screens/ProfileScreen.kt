package com.example.inventumopus.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.R


@Composable
fun ProfileScreen (
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    val isLoggedIn by viewModel.signedIn.collectAsState()

    if (isLoggedIn) {
        viewModel.getUser(viewModel.currentUser?.username!!)
    }
    val currentUser = viewModel.currentUser

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
                        text= currentUser?.username!!.uppercase(),
                        fontFamily = poppins,
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }

                // First row of cards
                Row (
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Experience
                    ProfileCard(
                        text = "Experience",
                        icon = R.drawable.work_bag_svgrepo_com,
                        onClick = {
                            navHostController.navigate("experience")
                        }
                    )

                    // Qualifications
                    ProfileCard(
                        text = "Qualifications",
                        icon = R.drawable.graduate,
                        width = 28.dp,
                        onClick = {
                            navHostController.navigate("qualifications")
                        }
                    )
                }

                // Second row of cards
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                   // Bookmarks
                    ProfileCard(
                        text = "Bookmarks",
                        icon = R.drawable.bookmarks,
                        onClick = { navHostController.navigate("bookmarks") }
                    )

                    // Applied Jobs
                    ProfileCard(
                        text = "Applied Jobs",
                        icon = R.drawable.pen_and_paper_svgrepo_com,
                        width = 25.dp,
                        onClick = {}
                    )
                }
            }
        } else {
            LoggedOutScreen(navHostController)
        }
    }
}

@Composable
fun ProfileCard(
    text: String,
    icon: Int,
    onClick: () -> Unit,
    width: Dp = 20.dp
) {
    Card(
        modifier = Modifier
            .padding(vertical = 15.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .width(170.dp)
                .height(75.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(id = icon),
                modifier = Modifier
                    .padding(end = 15.dp)
                    .width(width),
                contentDescription = "Work",
                contentScale = ContentScale.Fit
            )
            Text(
                text = text,
                fontFamily = poppins,
                fontWeight = FontWeight.Thin,
//                color = Color.DarkGray,
                fontSize = 14.sp
            )
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
//            color = Color.Gray,
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
//                    color = Color.DarkGray
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
//                    color = Color.DarkGray
                )
            }
        }
    }
}
