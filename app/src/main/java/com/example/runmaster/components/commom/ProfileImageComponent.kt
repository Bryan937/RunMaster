package com.example.runmaster.components.commom

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.example.runmaster.R
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.constants.CommonConstants.PROFILE_IMAGE_SIZE
import com.example.runmaster.constants.SplasScreenConstants
import com.example.runmaster.models.sessions.User
import com.example.runmaster.services.ImageService

@Composable
fun ProfileImageComponent(selectedImageUri: Uri?) {
    val context = LocalContext.current
    var base64Image by remember { mutableStateOf(User.instance.getInfoByLabel("Picture")) }
    var decodedBitmap by remember { mutableStateOf(ImageService.decodeBase64ToBitmap(base64Image)) }
    if (base64Image != User.instance.getInfoByLabel("Picture")) {
        base64Image = User.instance.getInfoByLabel("Picture")
        decodedBitmap = ImageService.decodeBase64ToBitmap(base64Image)
    }


    LaunchedEffect(selectedImageUri) {
        decodedBitmap = if (selectedImageUri != null) {
            val inputStream = context.contentResolver.openInputStream(selectedImageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmap
        } else {
            ImageService.decodeBase64ToBitmap(base64Image)
        }
    }


    Image(
        modifier = Modifier
            .padding(CommonConstants.SMALL_PADDING)
            .size(PROFILE_IMAGE_SIZE)
            .clip(CircleShape)
            .background(color = Color.Gray),
        painter =  decodedBitmap?.let { rememberAsyncImagePainter(it) }
            ?: painterResource(id = R.drawable.profile),
        contentDescription = SplasScreenConstants.LOGO_IMAGE_DESCRIPTION,
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )

}