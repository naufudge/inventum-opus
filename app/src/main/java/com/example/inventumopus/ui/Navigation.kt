package com.example.inventumopus.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.inventumopus.HomeViewModel
import com.example.inventumopus.ui.screens.HomeScreen
import com.example.inventumopus.ui.screens.JobDetailsScreen
import com.example.inventumopus.ui.screens.ProfileScreen
import com.example.inventumopus.ui.screens.SearchScreen
import com.example.inventumopus.ui.screens.SignInScreen
import com.example.inventumopus.ui.screens.SignUpScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object Details : Screen("job")
    data object Profile: Screen("profile")
    data object SignIn: Screen("signin")
    data object SignUp: Screen("signup")
}


@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier
) {
    val viewModel: HomeViewModel = viewModel()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {
            composable(Screen.Home.route) {
                HomeScreen(navHostController = navController, viewModel = viewModel)
            }
            composable(Screen.Search.route) {
                SearchScreen(navHostController = navController, viewModel = viewModel)
            }
            composable(Screen.Details.route) {
                JobDetailsScreen(navHostController = navController, viewModel = viewModel)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navHostController = navController, viewModel = viewModel)
            }
            composable(Screen.SignIn.route) {
                SignInScreen(navHostController = navController, viewModel = viewModel)
            }
            composable(Screen.SignUp.route) {
                SignUpScreen(navHostController = navController, viewModel = viewModel)
            }
    }

}