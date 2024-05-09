package com.example.runmaster.components.history_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.runmaster.R
import com.example.runmaster.components.commom.TextComponent
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.constants.CommonConstants.FIFTY_DP_UNITS
import com.example.runmaster.constants.CommonConstants.LARGE_PADDING
import com.example.runmaster.constants.CommonConstants.LARGE_SIZE
import com.example.runmaster.constants.CommonConstants.MEDIUM_PADDING
import com.example.runmaster.constants.CommonConstants.NORMAL_FONT_SIZE
import com.example.runmaster.constants.CommonConstants.ROTATE_ARROW_ANGLE
import com.example.runmaster.constants.CommonConstants.X_NORMAL_FONT_SIZE
import com.example.runmaster.models.sessions.Session
import com.example.runmaster.utils.navigation.Screen
import com.example.runmaster.view_models.OldSessionViewModel


@Composable
fun SessionComponent(navController: NavController, session: Session) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(MEDIUM_PADDING)
            .clickable {
                OldSessionViewModel.updateOldSession(session)
                navController.navigate(Screen.OldSession.route)},
    ) {
        DateRow(session)
        SessionRow(session)
    }
}

@Composable
fun DateRow(session: Session) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextComponent(
            textValue = session.dayOfWeek(),
            textSize = NORMAL_FONT_SIZE,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(),
            textAlign = TextAlign.Start,
            fontWeightValue = FontWeight.Bold,
            height = LARGE_SIZE
        )
        TextComponent(
            textValue = session.date,
            textSize = NORMAL_FONT_SIZE,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(),
            textAlign = TextAlign.End,
            fontWeightValue = FontWeight.Bold,
            height = LARGE_SIZE
        )
    }
}

@Composable
fun SessionRow(session: Session) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(CommonConstants.GRADIENT_COLORS),
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //stats
        BasicStats(modifier = Modifier.weight(1f), session)
        //arrow
        Image(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = null,
            modifier = Modifier
                .rotate(ROTATE_ARROW_ANGLE)
                .size(LARGE_SIZE)
        )
    }
}

@Composable
fun BasicStats(modifier: Modifier = Modifier, session: Session) {
    Row(
        modifier = modifier.wrapContentSize(), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.person_walk),
            contentDescription = null,
            modifier = Modifier.size(FIFTY_DP_UNITS)
        )
        Column(
            modifier = modifier
                .wrapContentHeight()
                .wrapContentHeight()
                .padding(LARGE_PADDING),
        ) {
            TextComponent(
                textValue = session.steps.toString() + " steps",
                textSize = X_NORMAL_FONT_SIZE,
                colorValue = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start,
                fontWeightValue = FontWeight.Bold,
                height = LARGE_SIZE
            )
            TextComponent(
                textValue = session.formatDuration(),
                textSize = X_NORMAL_FONT_SIZE,
                colorValue = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start
            )
        }
    }
}