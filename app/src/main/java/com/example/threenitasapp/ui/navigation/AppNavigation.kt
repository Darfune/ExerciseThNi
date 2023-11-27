package com.example.threenitasapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.threenitasapp.ui.screens.list.BookListScreen
import com.example.threenitasapp.ui.screens.list.BookListViewModel
import com.example.threenitasapp.ui.screens.login.LoginScreen
import com.example.threenitasapp.ui.screens.login.LoginViewModel

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    bookListViewModel: BookListViewModel,
) {
    NavHost(navController = navHostController, startDestination = AppScreens.LoginScreen.name) {
        composable(route = AppScreens.LoginScreen.name) {
            val state by loginViewModel.uiState.collectAsState()
            LoginScreen(
                state = state,
                viewModel = loginViewModel
            ) {
                navHostController.navigateToSingleTop(
                    route = AppScreens.BookListScreen.name + "?token=$it"
                )
            }
        }
        composable(route = AppScreens.BookListScreen.name + "?token={token}", arguments = listOf(
            navArgument("token") {
                NavType.StringType
                defaultValue = ""
            }
        )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            BookListScreen(
                token = token,
                viewModel = bookListViewModel
            )
        }
    }
}


fun NavHostController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}