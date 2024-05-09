package com.example.runmaster.components.camera_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.runmaster.components.commom.GradientButtonComponent
import com.example.runmaster.components.commom.TextComponent
import com.example.runmaster.constants.CommonConstants

@Composable
fun BottomSheetContent(onAddClick: () -> Unit, onTakeClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 100.dp)
            .wrapContentSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextComponent(
            textValue = "Ajouter une photo",
            textSize = CommonConstants.LARGE_FONT_SIZE,
            fontWeightValue = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .wrapContentSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GradientButtonComponent(
                    value = "Ajouter à partir de la galerie", onClick = onAddClick
                )
                Spacer(modifier = Modifier.height(CommonConstants.SPACER_SIZE))
                GradientButtonComponent(
                    value = "Prendre une photo", onClick = onTakeClick
                )
            }
        }
    }

}