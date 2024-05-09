package com.example.runmaster.view_models

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.runmaster.constants.ChronometerConstants
import com.example.runmaster.models.sessions.Session
import com.example.runmaster.models.sessions.User
import com.example.runmaster.services.Chronometer
import com.example.runmaster.utils.Position
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import java.util.UUID
import com.example.runmaster.view_models.OldSessionViewModel

object SessionManager {
    private lateinit var startDate: String

   fun getDate() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
        startDate = dateFormat.format(calendar.time)
    }

    // Calculates the distance between 2 coordinates
    private fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371 // radius of the earth in km
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = sin(latDistance / 2).pow(2.0) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(lonDistance / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }

    // Calculates the total distance by doing the sum of all recorded distances
    private fun calculateDistance(positions: List<Position>): Double {
        val totalDistance = positions.zipWithNext { a, b ->
            haversineDistance(a.latitude, a.longitude, b.latitude, b.longitude)
        }.sum()
        return totalDistance
    }

    // Saves a session
    fun saveSession(oldSession: OldSessionViewModel, positionTracker: PositionTracker) {
        val time = Chronometer.elapsedTime
        val steps = StepCounter.steps
        val currentTimeMillis = System.currentTimeMillis()
        val distance = calculateDistance(positionTracker.positions)
        val speed = (distance / time) * ChronometerConstants.MILLISECONDS_IN_HOUR
        val positions: List<Position> = positionTracker.positions
        val session = Session(
            sessionId = UUID.randomUUID().toString(),
            date = startDate,
            time = time,
            speed = speed,
            steps = steps,
            distance = distance,
            timestamp = currentTimeMillis,
            positions = positions,
        )
        User.instance.addToHistory(session)
        oldSession.updateOldSession(session)
    }
}