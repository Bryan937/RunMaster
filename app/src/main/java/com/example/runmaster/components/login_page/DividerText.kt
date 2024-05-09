package com.example.runmaster.components.login_page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.runmaster.constants.CommonConstants.MEDIUM_PADDING
import com.example.runmaster.constants.CommonConstants.SMALL_FONT_SIZE
import com.example.runmaster.constants.CommonConstants.THICKNESS

@Composable
fun DividerText() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            thickness = THICKNESS
        )
        Text(
            modifier = Modifier.padding(MEDIUM_PADDING),
            text = "or",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = SMALL_FONT_SIZE
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            thickness = THICKNESS
        )
    }
}

@Preview
@Composable
fun RegisterPagePreview() {
    DividerText()
}
