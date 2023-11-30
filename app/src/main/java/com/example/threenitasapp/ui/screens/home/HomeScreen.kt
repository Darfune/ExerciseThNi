package com.example.threenitasapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.threenitasapp.R
import com.example.threenitasapp.ui.navigation.home.HomeBottomNavigationScreens
import com.example.threenitasapp.ui.navigation.home.Link
import com.example.threenitasapp.ui.navigation.home.List
import com.example.threenitasapp.ui.navigation.home.Misc
import com.example.threenitasapp.ui.navigation.home.Play
import com.example.threenitasapp.ui.navigation.home.Settings
import com.example.threenitasapp.ui.navigation.home.HomeNavHost
import com.example.threenitasapp.ui.screens.home.list.BookListViewModel
import com.example.threenitasapp.ui.theme.Pink80
import com.example.threenitasapp.ui.theme.dark_jungle_green
import com.example.threenitasapp.ui.theme.onyx
import com.example.threenitasapp.ui.theme.white


@Preview
@Composable
fun NavGraphBuilder.HomeScreen(
    token: String = "",
    bookLoginViewModel: BookListViewModel = hiltViewModel(),
) {
    val bottomBarScreenList = listOf(List.route, Link.route, Play.route, Misc.route, Settings.route)
    val homeNavController: NavHostController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    HomeScreenScaffold(
        bottomBarScreenList,
        homeNavController,
        navBackStackEntry,
        currentDestination,
        bookLoginViewModel,
        token
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenScaffold(
    bottomBarScreenList: kotlin.collections.List<String>,
    homeNavController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    currentDestination: NavDestination?,
    bookLoginViewModel: BookListViewModel,
    token: String,
) {
    Scaffold(
        modifier = Modifier.background(dark_jungle_green),
        topBar = {
            CenterAlignedTopAppBar(

                title = {
                    HomeBottomNavigationScreens.forEach { screen ->
                        Text(
                            text = if (screen.route == currentDestination?.route) screen.title
                            else "",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = white
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(onyx)
            )
        },

        bottomBar = {
            if (bottomBarScreenList.contains(currentDestination?.route)) {
                CustomBottomAppBar(currentDestination, homeNavController)
            }
        }

    ) { it ->
        Surface(modifier = Modifier.padding(it), color = dark_jungle_green) {
            HomeNavHost(
                navController = homeNavController,
                token = token,
                bookLoginViewModel
            )
        }
    }
}


//@Preview
@Composable
fun CustomBottomAppBar(
    currentDestination: NavDestination?,
    homeNavController: NavHostController,
) {
    BottomAppBar(contentPadding = PaddingValues(0.dp), containerColor = Color.Transparent) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.tabs_bg),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            Image(
                painter = painterResource(id = R.drawable.tabs_wave),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Row(Modifier.background(Color.Transparent)) {
                HomeBottomNavigationScreens.forEach { item ->
                    NavigationBarItem(
                        selected = item.route == currentDestination?.route,
                        onClick = {
                            homeNavController.navigate(item.route) {
                                popUpTo(homeNavController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Image(
                                painter = painterResource(
                                    id = (
                                            (if (item.route == currentDestination?.route)
                                                item.selectedIcon
                                            else
                                                item.unselectedIcon))
                                ),
                                contentDescription = null
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(

                            indicatorColor = Color(
                                red = 0.0f,
                                green = 0.0f,
                                blue = 0.0f,
                                alpha = 0.0f
                            ),
                            selectedIconColor = Color.Red,
                            selectedTextColor = Color.Blue,
                            unselectedIconColor = Pink80,
                            unselectedTextColor = Color.Yellow,
                        )
                    )
                }
            }
        }
    }
}