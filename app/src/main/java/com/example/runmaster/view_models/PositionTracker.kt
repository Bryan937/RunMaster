package com.example.runmaster.view_models

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import com.example.runmaster.constants.ChronometerConstants
import com.example.runmaster.constants.TrackingConstants
import com.example.runmaster.services.Chronometer
import com.example.runmaster.services.LocationService
import com.example.runmaster.utils.Position
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PositionTracker(context: Context) {
    private var _positions: MutableList<Position> = mutableListOf()
    private var _currentSpeed by mutableDoubleStateOf(0.0)
    val positions: List<Position> get() = _positions
    val currentSpeed: Double get() = _currentSpeed
    private val fusedLocationClient = LocationService.getFusedLocationClient(context)
    private var locationRequest: LocationRequest = LocationRequest.Builder(TrackingConstants.LOCATION_UPDATE_INTERVAL)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .build()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                updatePosition(Position(location.latitude, location.longitude))
                updateSpeed(location.speed.toDouble())
            }
        }
    }

    private fun updateSpeed(speed: Double) {
        _currentSpeed = speed * TrackingConstants.MS_PER_SEC_TO_KM_PER_HOUR
    }

    private fun updatePosition(position: Position) {
        _positions.add(position)
    }

    fun startTracking() {
        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun pauseTracking() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        _currentSpeed = 0.0
    }

    fun endTracking() {
        _positions = emptyList<Position>().toMutableList()
    }
}
