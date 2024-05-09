package com.example.runmaster.components.commom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.models.sessions.User


// Not really stylish :/ but does the job
@Composable
fun ShowInformation(label: String){
    val textValue = "$label: ${User.instance.getInfoByLabel(label)}"
    // TODO: tried to wrap the box around the text size, not working for some reason

    Box(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(CommonConstants.GRADIENT_COLORS),
                shape = RoundedCornerShape(CommonConstants.ROUNDED_CORNER)
            )
            .wrapContentSize(Alignment.Center)
            .padding(horizontal = CommonConstants.SPACER_SIZE, vertical = CommonConstants.SPACER_SIZE),
    ) {
        TextComponent(
            textValue = textValue,
            textSize = CommonConstants.LARGE_FONT_SIZE,
            fontWeightValue = FontWeight.Bold,
        )
    }
}
