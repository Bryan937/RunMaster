package com.example.runmaster.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.runmaster.components.commom.TextComponent
import com.example.runmaster.components.history_screen.SessionComponent
import com.example.runmaster.models.sessions.Session
import com.example.runmaster.models.sessions.User

@Composable
fun HistoryScreen(navController: NavController) {
    var history by remember { mutableStateOf<List<Session>>(emptyList()) }
    var isDataLoaded by remember { mutableStateOf(false) }

    if (!isDataLoaded) {
        User.instance.retrieveFromDatabase { fetchedData ->
            history = fetchedData.sortedByDescending { it.timestamp }
            isDataLoaded = true
        }
    }

    if (isDataLoaded) {
        if (history.isEmpty()){
            TextComponent(textValue = "No sessions available." , textSize = 30.sp)
        } else {
            LazyColumn(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                items(history) { session ->
                    SessionComponent(navController = navController, session = session)
                }
            }
        }
    }
}