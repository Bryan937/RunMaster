package com.example.runmaster.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.runmaster.R
import com.example.runmaster.components.commom.GradientButtonComponent
import com.example.runmaster.components.login_page.PassWordTextField
import com.example.runmaster.components.commom.TextComponent
import com.example.runmaster.components.login_page.ClickableLoginTextComponent
import com.example.runmaster.components.login_page.DividerText
import com.example.runmaster.components.login_page.TextField
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.constants.CommonConstants.LARGE_FONT_SIZE
import com.example.runmaster.constants.CommonConstants.LARGE_PADDING
import com.example.runmaster.constants.CommonConstants.LARGE_SPACER_SIZE
import com.example.runmaster.constants.CommonConstants.X_NORMAL_FONT_SIZE
import com.example.runmaster.constants.CommonConstants.FIFTY_DP_UNITS
import com.example.runmaster.constants.CommonConstants.SPACER_SIZE
import com.example.runmaster.constants.SplasScreenConstants
import com.example.runmaster.models.sessions.User
import com.example.runmaster.services.DatabaseService
import com.example.runmaster.utils.LoginDataStore
import com.example.runmaster.utils.navigation.Screen

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val isEmailValid = remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(LARGE_PADDING)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .padding(CommonConstants.SMALL_PADDING)
                    .size(SplasScreenConstants.LOGO_SIZE),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = SplasScreenConstants.LOGO_IMAGE_DESCRIPTION,
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.height(SPACER_SIZE))
            TextComponent(
                textValue = stringResource(id = R.string.greetings),
                textSize = X_NORMAL_FONT_SIZE,
                fontWeightValue = FontWeight.Normal,
                height = FIFTY_DP_UNITS
            )
            TextComponent(
                textValue = stringResource(id = R.string.create_account),
                textSize = LARGE_FONT_SIZE,
                fontWeightValue = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(SPACER_SIZE))
            InputRegisterFields(isEmailValid)
            Spacer(modifier = Modifier.height(LARGE_SPACER_SIZE))
            GradientButtonComponent(value = "Register", onClick = {
                if (isEmailValid.value) {
                    val email = User.instance.getInfoByLabel("Email")
                    val password = User.instance.getInfoByLabel("Password")

                    DatabaseService.instance.createUser(email, password){ success, message ->
                        if (success) {
                            navController.navigate(Screen.SetProfileScreen.route)
                        }
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                } else
                    Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()
            })
            Spacer(modifier = Modifier.height(LARGE_SPACER_SIZE))
            DividerText()
            ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                navController.navigate(Screen.Login.route)
            })
        }

    }
}

@Composable
fun InputRegisterFields(isEmailValid: MutableState<Boolean>){
    TextField(
        labelValue = stringResource(id = R.string.first_name),
        painterResource(id = R.drawable.profile),
        {}
    )
    TextField(
        labelValue = stringResource(id = R.string.last_name),
        painterResource(id = R.drawable.profile),
        {}
    )
    TextField(
        labelValue = stringResource(id = R.string.email),
        painterResource(id = R.drawable.email),
        callback = { isValidEmail -> isEmailValid.value = isValidEmail },
        isEmailValid.value
    )
    PassWordTextField(
        labelValue = stringResource(id = R.string.password),
        painterResource(id = R.drawable.password),
        isPasswordIncorrect = false
    )
}


