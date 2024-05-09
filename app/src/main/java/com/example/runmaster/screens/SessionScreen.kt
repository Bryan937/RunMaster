package com.example.runmaster.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.runmaster.components.commom.RoundedButton
import com.example.runmaster.components.commom.TextWithSubTitle
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.utils.navigation.Screen
import com.example.runmaster.services.Chronometer
import com.example.runmaster.view_models.Acceleration
import com.example.runmaster.view_models.OldSessionViewModel
import com.example.runmaster.view_models.PositionTracker
import com.example.runmaster.view_models.SessionManager
import com.example.runmaster.view_models.StepCounter

@Composable
fun SessionScreen(navController: NavController, positionTracker: PositionTracker) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // time Section
            TimerComponent()
            CustomDivider(true)
            // Steps Section
            StepsComposable()
            CustomDivider(true)
            // Speed and Accel section
            SpeedAndAccelComposable(positionTracker)
            // buttons section
            ButtonsComposable(navController = navController, positionTracker)
        }
    }
}

@Composable
fun TimerComponent() {
    val timeString = Chronometer.formatTime(Chronometer.elapsedTime)
    TextWithSubTitle(value = timeString, subtitle ="TIME")
}

@Composable
fun StepsComposable() {
    TextWithSubTitle(value = StepCounter.steps.toString(), subtitle ="STEPS")
}

@Composable
fun SpeedAndAccelComposable(positionTracker: PositionTracker) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextWithSubTitle(
            value = String.format("%.2f", positionTracker.currentSpeed),
            subtitle = "SPEED \n (km/h)",
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth()
        )
        TextWithSubTitle(
            value = String.format("%.2f", Acceleration.currentAcceleration),
            subtitle = "ACCELERATION \n (m/s²)",
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth()
        )
    }
}
@Composable
fun CustomDivider(isVertical: Boolean) {
    Divider(
        modifier = if(isVertical) Modifier
            .fillMaxWidth() else Modifier
            .fillMaxHeight() ,
        color = MaterialTheme.colorScheme.onSurface,
        thickness = CommonConstants.THICKNESS
    )
}


@Composable
fun ButtonsComposable(navController: NavController, positionTracker: PositionTracker) {
    val currentContext = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        if (!Chronometer.isRunning) {
            RoundedButton(iconImage = Chronometer.playPauseIcon.value, onClick = {
                Chronometer.startChronometer()
                positionTracker.startTracking()
                SessionManager.getDate()
            })
            if (Chronometer.hasChronoStarted) {
                RoundedButton(iconImage = Icons.Filled.Stop, onClick = {
                    SessionManager.saveSession(OldSessionViewModel, positionTracker)
                    Chronometer.stopChronometer()
                    StepCounter.resetCounter(currentContext)
                    positionTracker.endTracking()
                    Acceleration.resetAcceleration()
                    navController.navigate(Screen.OldSession.route)
                })
            }

        } else {
            RoundedButton(iconImage = Chronometer.playPauseIcon.value, onClick = {
                Chronometer.pauseChronometer()
                positionTracker.pauseTracking()
                Acceleration.resetAcceleration()
            })
        }
    }
}