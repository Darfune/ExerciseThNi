package com.example.threenitasapp.ui.navigation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.threenitasapp.ui.navigation.root.Graphs
import com.example.threenitasapp.ui.screens.home.list.BookListScreen
import com.example.threenitasapp.ui.screens.home.list.BookListViewModel
import com.example.threenitasapp.ui.screens.home.misc.MiscScreen
import com.example.threenitasapp.ui.screens.home.play.PlayScreen
import com.example.threenitasapp.ui.screens.home.profile.ProfileScreen
import com.example.threenitasapp.ui.screens.home.settings.SettingsScreen

@Composable
fun HomeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = List.route,
        modifier = modifier
    ) {
        composable(route = List.route) {
            val bookViewModel = hiltViewModel<BookListViewModel>()
            BookListScreen(navController = navController, viewModel = bookViewModel)
        }
        composable(route = Link.route) {
            ProfileScreen()
        }

        composable(route = Link.route) {
            ProfileScreen()
        }
        composable(route = Play.route) {
            PlayScreen()
        }
        composable(route = Misc.route) {
            MiscScreen()
        }
        composable(route = Settings.route) {
            SettingsScreen()
        }
    }


    fun NavHostController.navigateSingleTopTo(route: String) =
        this.navigate(route) {
            popUpTo(
                this@navigateSingleTopTo.graph.findStartDestination().id
            ) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
}