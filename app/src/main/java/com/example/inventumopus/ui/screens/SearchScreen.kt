package com.example.inventumopus.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.inventumopus.HomeViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.inventumopus.ui.GoogleFonts


@Composable
fun SearchScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    val searchText by viewModel.searchText.collectAsState()
    val jobs by viewModel.jobs.collectAsState()

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
        // Screen Title
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Search for a Job",
            fontFamily = prata,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))

        SearchBar(
            query = searchText,
            onQueryChange = viewModel::onSearchTextChange
        )
        Spacer(modifier = Modifier.height(20.dp))

        RecentListings(navHostController = navHostController, viewModel = viewModel, jobs = jobs)

    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(query) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onQueryChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text(text = "Search Job", fontFamily = raleway, fontSize = 14.sp) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { text = ""; onQueryChange("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear Icon")
                }
            }
        },
        singleLine = true
    )
}