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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.datamodels.Job
import com.example.inventumopus.R
import com.example.inventumopus.ui.GoogleFonts
import com.example.inventumopus.ui.components.JobListingCard
import com.example.inventumopus.ui.components.Loading

val font = GoogleFonts()
val prata = font.Prata
val poppins = font.Poppins
val raleway = font.Raleway

sealed class Category(val title: String, val icon: Int) {
    data object Accounting: Category(title = "Accounting", icon = R.drawable.accounting)
    data object Software: Category(title = "Software", icon = R.drawable.software)
    data object Medical: Category(title = "Medical", icon = R.drawable.medical)
    data object Teaching: Category(title = "Teaching", icon = R.drawable.teaching)
    data object Hardware: Category(title = "Hardware", icon = R.drawable.tools)
    data object Carpentry: Category(title = "Carpentry", icon = R.drawable.carpentry)
    data object Administration: Category(title = "Administration", icon = R.drawable.administration)
}

@Composable
fun HomeScreen (
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    val jobsData = viewModel.jobsData.collectAsState()
    val user = viewModel.currentUser

    val isLoggedIn by viewModel.signedIn.collectAsState()

    val categories = listOf(Category.Accounting, Category.Software, Category.Medical, Category.Teaching, Category.Hardware, Category.Carpentry, Category.Administration)

    Column (
        modifier = Modifier
            .padding(top = 15.dp, start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        // Welcome text row
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
                // Displays username if logged in, else display "Guest"
                if (isLoggedIn) {
                    Text (
                        text = user?.username!!.uppercase(),
                        fontSize = 30.sp,
                        fontFamily = raleway,
                        fontWeight = FontWeight.W700
                    )
                } else {
                    Text (
                        text = "Guest",
                        fontSize = 30.sp,
                        fontFamily = raleway,
                        fontWeight = FontWeight.W700
                    )
                }

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
            PopularCategories(categories = categories)
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
            RecentListings(
                navHostController = navHostController,
                jobs = jobsData.value,
                viewModel = viewModel
            )
        }

    }
}

@Composable
fun PopularCategories(
    categories: List<Category>
) {
    LazyRow {
        itemsIndexed(categories) { _, category ->
            Spacer(modifier = Modifier.width(10.dp))
            PopularCategoryCard(item = category)
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun PopularCategoryCard(item: Category) {
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
            Icon(
                modifier = Modifier.size(50.dp),
                painter = painterResource(item.icon),
                contentDescription = item.title
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = item.title,
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
    val isLoading by viewModel::homeScreenLoading

    if (!isLoading) {
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            itemsIndexed(jobs) { _, job ->
                JobListingCard(navHostController = navHostController, jobItem = job, viewModel = viewModel)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    } else {
        Loading()
    }

}
