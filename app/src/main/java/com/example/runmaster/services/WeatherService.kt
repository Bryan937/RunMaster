package com.example.runmaster.services

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.runmaster.models.weather.CurrentWeather
import com.example.runmaster.utils.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

object WeatherService {
    var weatherResponse: MutableState<CurrentWeather?> = mutableStateOf(null)
    fun getCurrentWeather(ctx: Context) {
        val lat = LocationService.locationPosition.latitude
        val lon = LocationService.locationPosition.longitude

        GlobalScope.launch(Dispatchers.IO){
            val response  = try {
                RetrofitInstance.api.getCurrentWeather(lat, lon)
            }catch (error: IOException) {
               // Toast.makeText(ctx,"app error ${error.message}", Toast.LENGTH_SHORT ).show()
                return@launch
            } catch (error: retrofit2.HttpException) {
               // Toast.makeText(ctx,"app error ${error.message}", Toast.LENGTH_SHORT ).show()
                return@launch
            }
            if(response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main){
                    weatherResponse.value = response.body()!!
                }
            }
        }
    }
}
