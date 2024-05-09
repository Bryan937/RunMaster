package com.example.runmaster.components.commom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.runmaster.constants.ChronometerConstants
import com.example.runmaster.constants.CommonConstants.NULL_DP

@Composable
fun TextComponent(
    textValue: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    textSize: TextUnit,
    colorValue: Color = MaterialTheme.colorScheme.onBackground,
    fontWeightValue: FontWeight = FontWeight.Light,
    fontStyleValue: FontStyle = FontStyle.Normal,
    height: Dp = NULL_DP
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = height),
        text = textValue,
        color = colorValue,
        style = androidx.compose.ui.text.TextStyle(
            fontSize = textSize,
            fontWeight = fontWeightValue,
            fontStyle = fontStyleValue,
        ),
        textAlign = textAlign

    )
}

fun formatTime(timeInMillis: Long): String {
    val totalSeconds = timeInMillis / ChronometerConstants.TIME_INTERVAL
    val hours = totalSeconds / ChronometerConstants.SECONDS_IN_HOUR
    val minutes = (totalSeconds % ChronometerConstants.SECONDS_IN_HOUR) / ChronometerConstants.SECONDS_IN_MINUTE
    val seconds = totalSeconds % ChronometerConstants.SECONDS_IN_MINUTE
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}