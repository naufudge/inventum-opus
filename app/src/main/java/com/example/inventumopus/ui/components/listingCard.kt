package com.example.inventumopus.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.Job
import com.example.inventumopus.ui.GoogleFonts

@Composable
fun JobListingCard (
    navHostController: NavHostController,
    viewModel: HomeViewModel,
    jobItem: Job
) {
    val font = GoogleFonts()
    val poppins = font.Poppins
    val raleway = font.Raleway

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .size(width = 350.dp, height = 150.dp)
            .clickable {
                // handle click here
                navHostController.navigate(route = "job")
                viewModel.selectedJob(jobItem)
            }
    ) {

        Row (
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Job Image
            Column (
                modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp)),
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(jobItem.image)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = null)
            }

            // Job Info
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    fontFamily = FontFamily.Serif,
                    fontSize = 12.sp,
                    text = jobItem.name,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    fontSize = 12.sp,
                    text = jobItem.salary,
                    fontFamily = raleway

                )
                Text (
                    fontSize = 12.sp,
                    text = jobItem.location,
                    fontFamily = raleway
                )
            }
        }

    }
}