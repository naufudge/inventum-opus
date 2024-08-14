package com.example.inventumopus.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.Job
import com.example.inventumopus.R
import com.example.inventumopus.ui.GoogleFonts
import com.example.inventumopus.ui.components.JobListingCard

val font = GoogleFonts()
val prata = font.Prata
val poppins = font.Poppins
val raleway = font.Raleway

sealed class Category(val title: String, icon: Int) {
    data object Accounting: Category(title = "Accounting", icon = R.drawable.baseline_notifications_24)
    data object Software: Category(title = "Software", icon = R.drawable.baseline_notifications_24)
    data object Medical: Category(title = "Medical", icon = R.drawable.baseline_notifications_24)
    data object Teaching: Category(title = "Teaching", icon = R.drawable.baseline_notifications_24)
    data object Hardware: Category(title = "Hardware", icon = R.drawable.baseline_notifications_24)
    data object Carpentry: Category(title = "Carpentry", icon = R.drawable.baseline_notifications_24)
    data object Administration: Category(title = "Administration", icon = R.drawable.baseline_notifications_24)
}

@Composable
fun HomeScreen (
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    val jobsData = viewModel.jobsData.collectAsState()
    val user = "Guest"

    val exampleCategories = listOf("Accounting", "Software", "Medical", "Teaching", "Hardware", "Carpentry", "Administrative")

    Column (
        modifier = Modifier
            .padding(top = 15.dp, start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Column (
                modifier = Modifier
                    .padding(top = 20.dp, start = 15.dp),
            ) {
                Text (
                    text = "Welcome,",
                    fontFamily = raleway
                )
                Text (
                    text = user,
                    fontSize = 30.sp,
                    fontFamily = raleway,
                    fontWeight = FontWeight.W700
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        // Categories Row
        Column (
            modifier = Modifier.padding(vertical = 15.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = "Popular Categories",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            PopularCategories(categories = exampleCategories)
        }

        // Recent Listings
        Column (
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Text (
                modifier = Modifier.padding(15.dp),
                text = "Recent Listings",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            )
            RecentListings(navHostController = navHostController, jobs = jobsData.value, viewModel = viewModel)
        }

    }
}

@Composable
fun PopularCategories(
    categories: List<String>
) {
    LazyRow () {
        itemsIndexed(categories) { _, category ->
            Spacer(modifier = Modifier.width(10.dp))
            PopularCategoryCard(name = category)
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun PopularCategoryCard(name: String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .size(width = 125.dp, height = 125.dp)
            .clickable {
                // handle click here
                // navHostController.navigate("search")
            }

    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                fontSize = 13.sp,
            )
        }

    }
}

@Composable
fun RecentListings(
    navHostController: NavHostController,
    viewModel: HomeViewModel,
    jobs: List<Job>
) {
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        itemsIndexed(jobs) { _, job ->
            JobListingCard(navHostController = navHostController, jobItem = job, viewModel = viewModel)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
