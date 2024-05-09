package com.example.runmaster.components.login_page

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.runmaster.R
import com.example.runmaster.constants.CommonConstants.NORMAL_FONT_SIZE

@Composable
fun ClickableLoginTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {

    val initValue =
        if (tryingToLogin) stringResource(id = R.string.have_account) else stringResource(id = R.string.no_account)
    val loginText =
        if (tryingToLogin) stringResource(id = R.string.login) else stringResource(id = R.string.register)

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = NORMAL_FONT_SIZE
            )
        ) {
            append(" $initValue")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = NORMAL_FONT_SIZE
            )
        ) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(" $loginText")
        }
    }

    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
            if (span.item == loginText) {
                onTextSelected(span.item)
            }
        }
    })
}