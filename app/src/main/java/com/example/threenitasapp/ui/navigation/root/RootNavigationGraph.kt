package com.example.threenitasapp.ui.navigation.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.threenitasapp.ui.screens.home.HomeScreen
import com.example.threenitasapp.ui.screens.home.list.BookListViewModel
import com.example.threenitasapp.ui.screens.login.LoginScreen
import com.example.threenitasapp.ui.screens.login.LoginViewModel

@Composable
fun RootNavigationGraph(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    bookLoginViewModel: BookListViewModel,
) {
    NavHost(
        navController = navHostController,
        route = Graphs.Root.name,
        startDestination = Graphs.Login.name
    ) {
        composable(route = Graphs.Login.name) {
            val state by loginViewModel.uiState.collectAsState()
            LoginScreen(
                state = state,
                viewModel = loginViewModel
            ) {
                navHostController.navigateToSingleTop(
                    route = Graphs.Home.name + "?token=$it"
                )
            }
        }
        composable(route = Graphs.Home.name + "?token={token}", arguments = listOf(
            navArgument("token") {
                NavType.StringType
                defaultValue = ""
            }
        )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            HomeScreen(
                token = token,
                bookLoginViewModel = bookLoginViewModel
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

enum class Graphs {
    Root,
    Login,
    Home
}