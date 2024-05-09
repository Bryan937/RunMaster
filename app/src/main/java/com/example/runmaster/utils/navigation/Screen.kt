package com.example.runmaster.utils.navigation

sealed class Screen(val route: String) {
    object Home: Screen(route = "Home")
    object Register: Screen(route = "Register")
    object Profile: Screen(route = "Profile")
    object History: Screen(route = "History")
    object Login: Screen(route = "Login")
    object OldSession: Screen(route = "OldSession")
    object Session: Screen(route = "Session")
    object SetProfileScreen: Screen(route = "SetProfileScreen")
}
