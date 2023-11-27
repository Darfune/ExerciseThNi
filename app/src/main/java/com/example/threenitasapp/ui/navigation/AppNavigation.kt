package com.example.threenitasapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.threenitasapp.ui.screens.login.LoginScreen
import com.example.threenitasapp.ui.screens.login.LoginViewModel

@Composable
fun AppNavigation(navHostController: NavHostController, loginViewModel: LoginViewModel) {
    NavHost(navController = navHostController, startDestination = AppScreens.LoginScreen.name){
        composable(route = AppScreens.LoginScreen.name){
            val state by loginViewModel.uiState.collectAsState()
            LoginScreen(
                state = state,
                viewModel = loginViewModel
            )
        }
    }

}