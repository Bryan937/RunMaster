package com.example.runmaster.components.home_page


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.runmaster.components.commom.GradientButtonComponent
import com.example.runmaster.services.Chronometer
import com.example.runmaster.constants.CommonConstants.FIFTY_DP_UNITS
import com.example.runmaster.services.LocationService
import com.example.runmaster.services.WeatherService
import com.example.runmaster.utils.navigation.Screen
import com.example.runmaster.view_models.SessionManager
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MapView(navController: NavController) {
    val context = LocalContext.current
    WeatherService.getCurrentWeather(context)
    val devicePosition = LatLng(
        LocationService.locationPosition.latitude,
        LocationService.locationPosition.longitude
    )
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(devicePosition, 15f)
    }

    val buttonText = if (Chronometer.isRunning) "Check run"  else "Start run"

    Box(modifier = Modifier.fillMaxSize())
    {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPosition,
            uiSettings = MapUiSettings(compassEnabled = true, myLocationButtonEnabled = true)
        ) {
            Marker(
                state = MarkerState(position = devicePosition),
                title = "That's you, DUH!",
            )
        }
    }
    Box(modifier = Modifier.padding(start = FIFTY_DP_UNITS, top = FIFTY_DP_UNITS)) {
        Compass()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            WeatherCardComponent()
            Spacer(modifier = Modifier.height(20.dp))
            GradientButtonComponent(value = buttonText,  onClick = {
                navController.navigate(Screen.Session.route)
            })
        }
    }

}