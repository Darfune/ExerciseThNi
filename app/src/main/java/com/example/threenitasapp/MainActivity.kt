package com.example.threenitasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.threenitasapp.ui.navigation.AppNavigation
import com.example.threenitasapp.ui.screens.list.BookListScreen
import com.example.threenitasapp.ui.screens.list.BookListViewModel
import com.example.threenitasapp.ui.screens.login.LoginScreen
import com.example.threenitasapp.ui.screens.login.LoginViewModel
import com.example.threenitasapp.ui.theme.ThreenitasAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThreenitasAppTheme {
//                val navController = rememberNavController()
//                val loginViewModel: LoginViewModel = viewModel()
                val bookListViewModel: BookListViewModel = viewModel()
//                AppNavigation(
//                    navHostController = navController,
//                    loginViewModel = loginViewModel,
//                    bookListViewModel = bookListViewModel
//                )
                BookListScreen(token = "", viewModel = bookListViewModel)
            }
        }
    }
}

