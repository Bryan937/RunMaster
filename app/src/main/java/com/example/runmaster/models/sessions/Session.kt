package com.example.runmaster.models.sessions

import com.example.runmaster.constants.ChronometerConstants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.runmaster.utils.Position

data class Session(
    val sessionId: String = "",
    val date: String = "",
    val time: Long = 0,
    val distance: Double = 0.0,
    val speed: Double = 0.0,
    val steps: Int = 0,
    val timestamp: Long = 0,
    val positions: List<Position> = emptyList()
) {
    fun dayOfWeek(): String {
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(date) ?: return ""

        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH) ?: return ""
    }

    fun formatDuration(): String {
        val totalSeconds = time / ChronometerConstants.TIME_INTERVAL
        val hours = totalSeconds / ChronometerConstants.SECONDS_IN_HOUR
        val minutes = (totalSeconds % ChronometerConstants.SECONDS_IN_HOUR) / ChronometerConstants.SECONDS_IN_MINUTE
        val seconds = totalSeconds % ChronometerConstants.SECONDS_IN_MINUTE

        return "$hours h $minutes m $seconds s"
    }
}