package com.example.inventumopus.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.datamodels.Job
import com.example.inventumopus.datamodels.JobIDs
import com.example.inventumopus.ui.GoogleFonts
import com.example.inventumopus.ui.components.Loading

/* TODO:
* - Include job application status.
*/

@Composable
fun JobApplicationsScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    val currentUser = viewModel.currentUser

    viewModel.getUserAppliedJobs(JobIDs(currentUser?.appliedJobs!!))
    val appliedJobs = viewModel.userAppliedJobs.collectAsState()

    val isLoading by viewModel::applicationsScreenLoading

    Column (
        modifier = Modifier
            .padding(
                top = 50.dp,
                start = 20.dp,
                end = 20.dp
            )
            .fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Applied Jobs",
                fontFamily = prata,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        if (!isLoading) {
            if (appliedJobs.value.isEmpty()) {
                Column (
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "You have not applied for any jobs yet.",
                        fontFamily = raleway,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            } else {
                RecentListings(
                    navHostController = navHostController,
                    viewModel = viewModel,
                    jobs = appliedJobs.value
                )
            }
        } else {
            Loading()
        }

    }
}

@Composable
fun JobApplicationCard (
    navHostController: NavHostController,
    viewModel: HomeViewModel,
    jobItem: Job
) {
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
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    fontFamily = prata,
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
//            Column (
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.End
//            ) {
//                Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "favorite")
//            }
        }

    }
}