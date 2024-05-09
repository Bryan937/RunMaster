package com.example.runmaster.services

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.runmaster.utils.Position
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object LocationService {
    var locationPosition: Position = Position(0.0, 0.0)

    // Return a fusedLocationClient object with a given context
    fun getFusedLocationClient(context: Context) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    // Gets the device geographical coordinates asynchronously
    suspend fun getDeviceLastKnownLocation(context: Context) : Position? = suspendCoroutine { continuation ->
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->

                if (location != null) {
                    locationPosition.setCoordinates(location.latitude, location.longitude)
                    continuation.resume(locationPosition)
                } else {
                    Toast.makeText(context, "Cannot get location", Toast.LENGTH_SHORT).show()
                    continuation.resume(null)
                }
            }
        }
    }
}