package com.example.threenitasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.threenitasapp.ui.navigation.root.RootNavigationGraph
import com.example.threenitasapp.ui.screens.home.list.BookListViewModel
import com.example.threenitasapp.ui.screens.login.LoginViewModel
import com.example.threenitasapp.ui.theme.ThreenitasAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThreenitasAppTheme {
                val navController = rememberNavController()
                val loginViewModel: LoginViewModel = viewModel()
                val bookLoginViewModel: BookListViewModel = viewModel()
                RootNavigationGraph(
                    navHostController = navController,
                    loginViewModel,
                    bookLoginViewModel,
                )
            }
        }
    }
}

