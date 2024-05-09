package com.example.runmaster.components.commom

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.services.Chronometer

@Composable
fun RoundedButton(iconImage: ImageVector, onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val buttonWidth =
        if (screenWidthDp < CommonConstants.MOBILE_THRESHOLD) CommonConstants.MOBILE_BUTTON_WIDTH else CommonConstants.TABLET_BUTTON_WIDTH

    Button(
        onClick = onClick,
        colors = if (iconImage == Icons.Filled.Stop) ButtonDefaults.buttonColors(Color.Red) else ButtonDefaults.buttonColors(),
        modifier = Modifier
            .size(90.dp)
            .clip(CircleShape),
    ) {
        Box(
            modifier = Modifier
                .width(buttonWidth)
                .heightIn(CommonConstants.BUTTON_HEIGHT),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = iconImage, contentDescription = null)
        }
    }
}

