package com.example.runmaster.components.login_page

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.runmaster.R
import com.example.runmaster.constants.CommonConstants.EXTRA_LARGE_PADDING
import com.example.runmaster.constants.CommonConstants.LARGE_PADDING
import com.example.runmaster.constants.CommonConstants.ROUNDED_CORNER
import com.example.runmaster.models.sessions.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(labelValue: String, painterResource: Painter,callback: (Boolean) -> Unit, isValidEmail: Boolean = true) {
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(ROUNDED_CORNER))
            .padding(
                start = EXTRA_LARGE_PADDING,
                top = LARGE_PADDING,
                end = EXTRA_LARGE_PADDING),
        label = { Text(text = labelValue) },
        value = textValue.value,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = if (!isValidEmail) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            focusedBorderColor = if (!isValidEmail) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            focusedLabelColor = if (!isValidEmail) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
        ),
        leadingIcon = {Icon(painter = painterResource, contentDescription = null)},
        keyboardOptions = KeyboardOptions.Default,
        onValueChange = {
            textValue.value = it

            if (labelValue == "Email") {
                if (User.instance.isValidEmail(it)) User.instance.setInfoByLabel(labelValue, it)
                callback(User.instance.isValidEmail(it))
            } else {
                User.instance.setInfoByLabel(labelValue, it)
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassWordTextField(labelValue: String, painterResource: Painter, isPasswordIncorrect: Boolean) {
    val passwordValue = remember {
        mutableStateOf("")
    }
    val passWordVisible =  remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(ROUNDED_CORNER))
            .padding(
                start = EXTRA_LARGE_PADDING,
                top = LARGE_PADDING,
                end = EXTRA_LARGE_PADDING,
                bottom = LARGE_PADDING
            ),
        label = { Text(text = labelValue) },
        value = passwordValue.value,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isPasswordIncorrect) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = if (isPasswordIncorrect) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            cursorColor = if (isPasswordIncorrect) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
        ),
        leadingIcon = {Icon(painter = painterResource, contentDescription = null)},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {
            passwordValue.value = it
            User.instance.setInfoByLabel(labelValue, it)
        },
        trailingIcon = {
            val  iconImage = if(passWordVisible.value) {
                Icons.Filled.Visibility
            } else Icons.Filled.VisibilityOff

            val description = if(passWordVisible.value) {
                "Hide password"
            } else "show password"
            IconButton(onClick = { passWordVisible.value = !passWordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passWordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    
    )
}