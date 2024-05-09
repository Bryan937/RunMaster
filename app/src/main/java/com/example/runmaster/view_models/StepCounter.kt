package com.example.runmaster.view_models
import android.content.Context
import android.hardware.SensorEvent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.example.runmaster.services.Chronometer

object StepCounter {
    private var _steps by mutableIntStateOf(0)
    private var previousTotalSteps = 0
    private var totalSteps = 0
    private var stepsWhilePaused = 0
    private var areStepsCounting = false
    val steps: Int get() = _steps

    // Saves the number of steps in the app private storage
    fun saveData(context: Context) {
        val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("totalSteps", previousTotalSteps.toFloat())
        editor.apply()
    }

    // Loads number of steps from the app private storage
    fun loadData(context: Context) {
        val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val totalStepsSaved = sharedPreferences.getFloat("totalSteps", 0f)
        previousTotalSteps = totalStepsSaved.toInt()
    }

    // Reset all the step counter attributes
    fun resetCounter(context: Context) {
        previousTotalSteps = totalSteps
        _steps = 0
        stepsWhilePaused = 0
        areStepsCounting = false
        saveData(context)
    }

    // Calculate the steps when there is a step counter event
    fun handleStepCounterEvent(event: SensorEvent) {
        totalSteps = event.values[0].toInt()

        if (!areStepsCounting) {
            previousTotalSteps = totalSteps
        }

        if (Chronometer.isRunning) {
            areStepsCounting = true
            _steps = totalSteps - previousTotalSteps - stepsWhilePaused
        } else if (areStepsCounting) {
            stepsWhilePaused = totalSteps - previousTotalSteps - _steps
        }
    }
}