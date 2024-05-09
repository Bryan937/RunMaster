package com.example.runmaster.components.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.runmaster.R
import com.example.runmaster.constants.CommonConstants.GRADIENT_COLORS
import com.example.runmaster.constants.CommonConstants.X_NORMAL_FONT_SIZE
import com.example.runmaster.constants.CommonConstants.SMALL_PADDING
import com.example.runmaster.constants.SplasScreenConstants.APP_NAME
import com.example.runmaster.constants.SplasScreenConstants.LOGO_COLOR
import com.example.runmaster.constants.SplasScreenConstants.LOGO_IMAGE_DESCRIPTION
import com.example.runmaster.constants.SplasScreenConstants.LOGO_SIZE
import com.example.runmaster.constants.SplasScreenConstants.ORANGE_COLOR

@Composable
fun SplashScreenLogo() {
    Surface(
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .padding(SMALL_PADDING)
                    .size(LOGO_SIZE),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = LOGO_IMAGE_DESCRIPTION
            )
            Text(
                text = APP_NAME,
                fontSize = X_NORMAL_FONT_SIZE,
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


}

@Preview
@Composable
fun LogoPreview() {
    SplashScreenLogo()
}
