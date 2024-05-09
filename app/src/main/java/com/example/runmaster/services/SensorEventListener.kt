package com.example.runmaster.services

import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.example.runmaster.view_models.Acceleration
import com.example.runmaster.view_models.StepCounter

object SensorEventListener: SensorEventListener {
    var onCompassOrientationChanged: ((Float) -> Unit)? = null
    private var rotationMatrix = FloatArray(9)
    private var orientationMatrix = FloatArray(3)
    private var gravityMatrix = FloatArray(3)
    private var geoMagnetic = FloatArray(3)

     // Handles event when a sensor's value changes
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            when (event.sensor.type) {
                Sensor.TYPE_MAGNETIC_FIELD -> {
                   geoMagnetic = event.values
                    this.computeValues(rotationMatrix, gravityMatrix, geoMagnetic, orientationMatrix)
                }
                Sensor.TYPE_ACCELEROMETER -> {
                    gravityMatrix = event.values
                }
                Sensor.TYPE_STEP_COUNTER -> {
                    StepCounter.handleStepCounterEvent(event)
                }
                Sensor.TYPE_LINEAR_ACCELERATION -> {
                    Acceleration.calculateAcceleration(event)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle sensor accuracy changes, if needed
    }

    // Compute the values for the compass orientation
    private fun computeValues(rotationMtrx: FloatArray, gravityMtrx: FloatArray, geoMtrx: FloatArray, orientationMtrx: FloatArray  ) {
        SensorManager.getRotationMatrix(rotationMtrx, null, gravityMtrx, geoMtrx)
        SensorManager.getOrientation(rotationMtrx, orientationMtrx)
        onCompassOrientationChanged?.invoke(orientationMatrix[0] * 180/3.14159.toFloat())
    }
}