package com.example.runmaster.utils

import androidx.navigation.NavBackStackEntry
import com.example.runmaster.constants.CommonConstants.HOME_INDEX
import com.example.runmaster.utils.navigation.Screen

object ShowTopBottomBar {
    var selectedItem: Int = HOME_INDEX
    fun showTopBar(navBackStackEntry: NavBackStackEntry): Boolean {
        return when (navBackStackEntry.destination.route) {
            Screen.Register.route -> false
            Screen.Login.route -> false
            Screen.Session.route -> false
            Screen.Profile.route -> false
            Screen.SetProfileScreen.route -> false
            else -> true
        }
    }

    fun showBottomBar(navBackStackEntry: NavBackStackEntry): Boolean {
        return when (navBackStackEntry.destination.route) {
            Screen.Register.route -> false
            Screen.Login.route -> false
            Screen.Session.route -> false
            Screen.SetProfileScreen.route -> false
            else -> true
        }
    }
}