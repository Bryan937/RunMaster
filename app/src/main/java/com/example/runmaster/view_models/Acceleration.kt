package com.example.runmaster.view_models

import android.hardware.SensorEvent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import com.example.runmaster.services.Chronometer
import kotlin.math.pow
import kotlin.math.sqrt

object Acceleration {
    private var _currentAcceleration by mutableFloatStateOf(0f)
    val currentAcceleration: Float get() = _currentAcceleration

    // Calculates the acceleration magnitude using the linear accelerometer sensor
    fun calculateAcceleration(event: SensorEvent) {
        if (Chronometer.isRunning) {
            val linearAcceleration = floatArrayOf(event.values[0], event.values[1], event.values[2])
            val accelerationMagnitude = sqrt(
                linearAcceleration[0].toDouble().pow(2.0) +
                        linearAcceleration[1].toDouble().pow(2.0) +
                        linearAcceleration[2].toDouble().pow(2.0)
            ).toFloat()
            _currentAcceleration = accelerationMagnitude
        }
    }

    fun resetAcceleration() {
        _currentAcceleration = 0F
    }
}