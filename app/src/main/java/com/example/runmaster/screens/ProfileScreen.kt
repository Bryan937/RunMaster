package com.example.runmaster.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.runmaster.R
import com.example.runmaster.components.commom.GradientButtonComponent
import com.example.runmaster.components.commom.ProfileImageComponent
import com.example.runmaster.components.commom.ShowInformation
import com.example.runmaster.components.commom.TextComponent
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.constants.CommonConstants.PROFILE_INDEX
import com.example.runmaster.constants.CommonConstants.SMALL_FONT_SIZE
import com.example.runmaster.constants.CommonConstants.SMALL_PADDING
import com.example.runmaster.constants.CommonConstants.SMALL_ROUNDED_CORNER
import com.example.runmaster.constants.CommonConstants.SMALL_SPACER_SIZE
import com.example.runmaster.utils.ShowTopBottomBar
import com.example.runmaster.utils.navigation.Screen
import com.example.runmaster.services.DatabaseService
import com.example.runmaster.utils.LoginDataStore
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, loginDataStore: LoginDataStore) {
    ShowTopBottomBar.selectedItem = PROFILE_INDEX

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(CommonConstants.LARGE_PADDING)
    ) {


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val coroutineScope = rememberCoroutineScope()
            ProfileImageComponent(null)
            Spacer(modifier = Modifier.height(SMALL_SPACER_SIZE))
            Box(modifier = Modifier
                .wrapContentSize()
                .background(
                    color = Color.Gray, shape = RoundedCornerShape(SMALL_ROUNDED_CORNER)
                )
                .padding(SMALL_PADDING)
                .clickable {
                    navController.navigate(Screen.SetProfileScreen.route)
                }) {
                Row(
                    Modifier.wrapContentSize(), horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(id = R.string.edit),
                        fontSize = SMALL_FONT_SIZE,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(SMALL_SPACER_SIZE))
                    Image(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }
            }
            Spacer(modifier = Modifier.height(CommonConstants.SPACER_SIZE))

            TextComponent(
                textValue = stringResource(id = R.string.welcome),
                textSize = CommonConstants.LARGE_FONT_SIZE,
                fontWeightValue = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(CommonConstants.LARGE_SPACER_SIZE))

            ShowInformation("First name")
            Spacer(modifier = Modifier.height(CommonConstants.SPACER_SIZE))
            ShowInformation("Last name")
            Spacer(modifier = Modifier.height(CommonConstants.SPACER_SIZE))
            ShowInformation("Email")
            Spacer(modifier = Modifier.height(CommonConstants.LARGE_SPACER_SIZE))

            GradientButtonComponent(value = "Log out", onClick = {
                coroutineScope.launch {
                    loginDataStore.saveUserCredentials("", "")
                    loginDataStore.saveLoginState(false)
                }
                DatabaseService.instance.signOut()
                navController.navigate(Screen.Login.route)
            })

        }
    }
}


