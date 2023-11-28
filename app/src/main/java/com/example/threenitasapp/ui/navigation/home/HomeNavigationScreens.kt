package com.example.threenitasapp.ui.navigation.home

import com.example.threenitasapp.R

interface HomeNavigationScreens {
    val route: String
    val title: String
    val selectedIcon: Int
    val unselectedIcon: Int
}

object List : HomeNavigationScreens {
    override val route = "list"
    override val title = "Magazines"
    override val selectedIcon = R.drawable.ic_book_sel
    override val unselectedIcon = R.drawable.ic_book
}

object Link : HomeNavigationScreens {
    override val route = "link"
    override val title = "Links"
    override val selectedIcon = R.drawable.ic_link_sel
    override val unselectedIcon = R.drawable.ic_link
}
object Play : HomeNavigationScreens {
    override val route = "play"
    override val title = "Play"
    override val selectedIcon = R.drawable.btn_pause
    override val unselectedIcon = R.drawable.btn_play
}
object Misc : HomeNavigationScreens {
    override val route = "misc"
    override val title = "Misc"
    override val selectedIcon = R.drawable.ic_misc_sel
    override val unselectedIcon = R.drawable.ic_misc
}
object Settings : HomeNavigationScreens {
    override val route = "settings"
    override val title = "Settings"
    override val selectedIcon = R.drawable.ic_settings_sel
    override val unselectedIcon = R.drawable.ic_settings
}


val HomeBottomNavigationScreens = listOf(List, Link, Play, Misc, Settings)