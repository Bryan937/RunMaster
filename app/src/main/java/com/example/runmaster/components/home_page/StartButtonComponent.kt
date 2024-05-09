package com.example.runmaster.components.home_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.runmaster.components.commom.TextComponent
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.utils.navigation.Screen

@Composable
fun StartSession(navController: NavController, value: String) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val buttonWidth =
        if (screenWidthDp < CommonConstants.MOBILE_THRESHOLD) CommonConstants.MOBILE_BUTTON_WIDTH else CommonConstants.TABLET_BUTTON_WIDTH

    Button(
        onClick = { navController.navigate(Screen.Session.route)
        },
        modifier = Modifier
            .width(buttonWidth)
            .heightIn(CommonConstants.BUTTON_HEIGHT),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .width(buttonWidth)
                .heightIn(CommonConstants.BUTTON_HEIGHT)
                .background(
                    brush = Brush.horizontalGradient(CommonConstants.GRADIENT_COLORS),
                    shape = RoundedCornerShape(
                        CommonConstants.FIFTY_DP_UNITS
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            TextComponent(
                textValue = value,
                textSize = CommonConstants.NORMAL_FONT_SIZE,
                fontWeightValue = FontWeight.Bold
            )
        }
    }
}