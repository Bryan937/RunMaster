package com.example.runmaster.utils

import androidx.compose.material.icons.Icons

object WeatherIconConverter {
    fun convertIcon(icon: String): String {
        return "https://openweathermap.org/img/wn/$icon@2x.png"
    }
}