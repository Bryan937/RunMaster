package com.example.runmaster.network

import com.example.runmaster.constants.WeatherConstants.WEATHER_API_KEY
import com.example.runmaster.models.weather.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lng: Double = 0.0,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey : String = WEATHER_API_KEY ,
        ): Response<CurrentWeather>
}