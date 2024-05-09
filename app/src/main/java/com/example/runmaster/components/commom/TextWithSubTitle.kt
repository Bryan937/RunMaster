package com.example.runmaster.components.commom

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.runmaster.constants.CommonConstants.EXTRA_LARGE_FONT_SIZE
import com.example.runmaster.constants.CommonConstants.X_NORMAL_FONT_SIZE

@Composable
fun TextWithSubTitle(value: String, subtitle: String,  modifier: Modifier = Modifier) {
    Column(modifier = modifier){
        TextComponent(textValue = value, textSize = EXTRA_LARGE_FONT_SIZE )
        TextComponent(textValue = subtitle, textSize = X_NORMAL_FONT_SIZE )
    }
}
