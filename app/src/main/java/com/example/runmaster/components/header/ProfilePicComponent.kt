package com.example.runmaster.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.runmaster.R
import com.example.runmaster.constants.SplasScreenConstants
import com.example.runmaster.constants.SplasScreenConstants.SMALL_LOGO_SIZE
import com.example.runmaster.models.sessions.User
import com.example.runmaster.services.ImageService
import com.example.runmaster.utils.navigation.Screen


@Composable
fun ProfilePicComponent(navController: NavController) {

    var base64Image by remember { mutableStateOf(User.instance.getInfoByLabel("Picture")) }
    var decodedBitmap by remember { mutableStateOf(ImageService.decodeBase64ToBitmap(base64Image)) }

    if (base64Image != User.instance.getInfoByLabel("Picture")) {
        base64Image = User.instance.getInfoByLabel("Picture")
        decodedBitmap = ImageService.decodeBase64ToBitmap(base64Image)
    }

    Box(modifier = Modifier
        .clickable {
            navController.navigate(Screen.Profile.route)
        }
        .size(SMALL_LOGO_SIZE)
        .clip(CircleShape)
        .background(color = Color.Gray),
        contentAlignment = Alignment.Center) {
        Image(modifier = Modifier.size(SplasScreenConstants.LOGO_SIZE),
            painter = decodedBitmap?.let { rememberAsyncImagePainter(it) }
                ?: painterResource(id = R.drawable.profile),
            contentDescription = "profile_pic",
            contentScale = ContentScale.Crop)
    }
}



