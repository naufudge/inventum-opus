package com.example.inventumopus.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.datamodels.JobIDs
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