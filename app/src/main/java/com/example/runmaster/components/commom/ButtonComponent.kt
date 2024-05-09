package com.example.runmaster.components.commom

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
import com.example.runmaster.constants.CommonConstants.BUTTON_HEIGHT
import com.example.runmaster.constants.CommonConstants.GRADIENT_COLORS
import com.example.runmaster.constants.CommonConstants.FIFTY_DP_UNITS
import com.example.runmaster.constants.CommonConstants.MOBILE_BUTTON_WIDTH
import com.example.runmaster.constants.CommonConstants.MOBILE_THRESHOLD
import com.example.runmaster.constants.CommonConstants.NORMAL_FONT_SIZE
import com.example.runmaster.constants.CommonConstants.TABLET_BUTTON_WIDTH

@Composable
fun GradientButtonComponent(value: String, onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val buttonWidth =
        if (screenWidthDp < MOBILE_THRESHOLD) MOBILE_BUTTON_WIDTH else TABLET_BUTTON_WIDTH

    Button(
        onClick = onClick,
        modifier = Modifier
            .width(buttonWidth)
            .heightIn(BUTTON_HEIGHT),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .width(buttonWidth)
                .heightIn(BUTTON_HEIGHT)
                .background(
                    brush = Brush.horizontalGradient(GRADIENT_COLORS),
                    shape = RoundedCornerShape(
                        FIFTY_DP_UNITS
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            TextComponent(
                textValue = value,
                textSize = NORMAL_FONT_SIZE,
                fontWeightValue = FontWeight.Bold
            )
        }
    }
}
