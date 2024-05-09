package com.example.runmaster.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.runmaster.R
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.constants.CommonConstants.GRADIENT_COLORS
import com.example.runmaster.constants.CommonConstants.LARGE_FONT_SIZE
import com.example.runmaster.constants.CommonConstants.SMALL_PADDING
import com.example.runmaster.constants.SplasScreenConstants
import com.example.runmaster.constants.SplasScreenConstants.LOGO_IMAGE_DESCRIPTION
import com.example.runmaster.constants.SplasScreenConstants.MEDIUM_LOGO_SIZE
import com.example.runmaster.constants.SplasScreenConstants.SMALL_LOGO_SIZE

@Composable
fun AppName(modifier: Modifier = Modifier) {
     Row(
         modifier = modifier.wrapContentSize(),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.End
     ){
         Image(
             modifier = Modifier
                 .padding(SMALL_PADDING)
                 .size(MEDIUM_LOGO_SIZE),
             painter = painterResource(id = R.drawable.ic_launcher_foreground),
             contentDescription = LOGO_IMAGE_DESCRIPTION,
             alignment = Alignment.Center
         )
         Text(
             text = SplasScreenConstants.APP_NAME,
             fontSize = LARGE_FONT_SIZE,
             fontWeight = FontWeight.ExtraBold,
             color = MaterialTheme.colorScheme.onSurface,
             style = TextStyle(
                 brush = Brush.linearGradient(
                     colors = GRADIENT_COLORS
                 )
             )
         )
     }
}

@Preview
@Composable
fun previewAppName(){
    AppName()
}