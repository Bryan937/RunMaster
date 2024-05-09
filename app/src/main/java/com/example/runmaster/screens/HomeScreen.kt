package com.example.runmaster.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.example.runmaster.components.home_page.MapView
import com.example.runmaster.models.sessions.User
import com.example.runmaster.utils.LoginDataStore
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen(navController: NavController, loginDataStore: LoginDataStore) {
    User.instance.updateDatabase() { _, _ -> null }
    MapView(navController)
    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        loginDataStore.saveUserCredentials(User.instance.getInfoByLabel("Email"), User.instance.getInfoByLabel("Password"))
        loginDataStore.saveLoginState(true)
    }
}