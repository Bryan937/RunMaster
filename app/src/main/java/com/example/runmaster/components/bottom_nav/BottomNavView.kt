package com.example.runmaster.components.bottom_nav

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.runmaster.utils.ShowTopBottomBar
import com.example.runmaster.utils.navigation.BottomNavigationItem
import com.example.runmaster.utils.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavView(navController: NavController) {
    var selectedItemIndex by remember {
        mutableStateOf(ShowTopBottomBar.selectedItem)
    }
    LaunchedEffect(key1 = ShowTopBottomBar.selectedItem) {
        selectedItemIndex = ShowTopBottomBar.selectedItem
    }
    val items = listOf(
        BottomNavigationItem(
            title = Screen.Profile.route,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        ), BottomNavigationItem(
            title = Screen.Home.route,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ), BottomNavigationItem(
            title = Screen.History.route,
            selectedIcon = Icons.Filled.Checklist,
            unselectedIcon = Icons.Outlined.Checklist
        )
    )
    NavigationBar {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(selected = selectedItemIndex == index, label = {
                    Text(text = item.title)
                }, onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.title)
                }, icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )

                })
            }
        }
    }
}
