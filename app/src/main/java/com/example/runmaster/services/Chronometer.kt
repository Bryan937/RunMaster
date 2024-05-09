package com.example.runmaster.services

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.runmaster.constants.ChronometerConstants
import java.util.Timer
import java.util.TimerTask

object Chronometer {
    private var timer: Timer? = null
    var isRunning by mutableStateOf(false)
    var elapsedTime by mutableLongStateOf(ChronometerConstants.INITIAL_TIME)
    var playPauseIcon = mutableStateOf(Icons.Filled.PlayArrow)
    var hasChronoStarted = false

    fun startChronometer() {
        hasChronoStarted = true
        isRunning = true
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (isRunning) {
                    elapsedTime += ChronometerConstants.TIME_INTERVAL
                } else {
                    timer?.cancel()
                    timer = null
                }
            }
        }, ChronometerConstants.TIME_INTERVAL, ChronometerConstants.TIME_INTERVAL)

        playPauseIcon.value = Icons.Filled.Pause
    }

    fun pauseChronometer() {
        isRunning = false
        playPauseIcon.value = Icons.Filled.PlayArrow
    }

    fun stopChronometer() {
        timer?.cancel()
        timer = null
        hasChronoStarted = false
        isRunning = false
        elapsedTime = ChronometerConstants.INITIAL_TIME
        playPauseIcon.value = Icons.Filled.PlayArrow
    }

    fun formatTime(timeInMillis: Long): String {
        val totalSeconds = timeInMillis / ChronometerConstants.TIME_INTERVAL
        val hours = totalSeconds / ChronometerConstants.SECONDS_IN_HOUR
        val minutes =
            (totalSeconds % ChronometerConstants.SECONDS_IN_HOUR) / ChronometerConstants.SECONDS_IN_MINUTE
        val seconds = totalSeconds % ChronometerConstants.SECONDS_IN_MINUTE
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
