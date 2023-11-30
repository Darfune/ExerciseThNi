package com.example.threenitasapp.ui.navigation.home

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.threenitasapp.ui.screens.home.list.BookListScreen
import com.example.threenitasapp.ui.screens.home.list.BookListViewModel
import com.example.threenitasapp.ui.screens.home.misc.MiscScreen
import com.example.threenitasapp.ui.screens.home.play.PlayScreen
import com.example.threenitasapp.ui.screens.home.profile.ProfileScreen
import com.example.threenitasapp.ui.screens.home.settings.SettingsScreen

@Composable
fun HomeNavHost(
    navController: NavHostController,
    token: String,
    bookLoginViewModel: BookListViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = List.route,
    ) {
        composable(route = List.route) {
            val state by bookLoginViewModel.uiRemoteState.collectAsStateWithLifecycle()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                BookListScreen(
                    token = token,
                    state = state,
                    navController = navController,
                    viewModel = bookLoginViewModel,
                    getDate = bookLoginViewModel::getDate,
                    getAllBooksFromDB = bookLoginViewModel::getAllBooksForDB,
                    getDownloadId = bookLoginViewModel::startDownload,
                    insertBookToDB = bookLoginViewModel::insertPDFtoDatabase
                )
            }
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