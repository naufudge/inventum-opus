package com.example.inventumopus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.ui.GoogleFonts

@Composable
fun JobDetailsScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    val job = viewModel.selectedJob

    val font = GoogleFonts()
    val raleway = font.Raleway
    val poppins = font.Poppins
    val prata = font.Prata

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Back Button
        OutlinedButton(
            onClick = { navHostController.popBackStack() }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back Icon")
        }
        Spacer(modifier = Modifier.height(80.dp))
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Job Image Card
            ElevatedCard(
                modifier = Modifier
                    .size(width = 130.dp, height = 130.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp)),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(job?.image)
                            .size(Size.ORIGINAL)
                            .build(),
                        contentDescription = null
                    )
                }

            }

            Spacer(modifier = Modifier.height(30.dp))

            // Job Title
            Text(
                text = job?.name!!,
                fontFamily = prata,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Company & Location
            Text(
                text = "${job.company} / ${job.location}",
                fontFamily = poppins,
                fontSize = 13.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        }
        Column (
            modifier = Modifier
                .padding(end = 10.dp, top = 25.dp)
                .width(200.dp)
        ) {
            // Job Salary
            Text(
                text = "Salary",
                fontFamily = raleway,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = job?.salary!!,
                fontFamily = raleway,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Job Qualification
            Text(
                text = "Qualification",
                fontFamily = raleway,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = job.qualification,
                fontFamily = raleway,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Job Experience
            Text(
                text = "Experience Required",
                fontFamily = raleway,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = job.experience,
                fontFamily = raleway,
                fontSize = 13.sp
            )
        }
        Column (
            modifier = Modifier.padding(top = 25.dp)
        ) {
            // Job Description
            Text(
                text = "Job Description",
                fontFamily = raleway,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = job?.description!!,
                fontFamily = raleway,
                fontSize = 13.sp,
                textAlign = TextAlign.Justify
            )
        }
    }

}