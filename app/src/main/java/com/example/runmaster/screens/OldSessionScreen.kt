package com.example.runmaster.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.runmaster.components.commom.TextComponent
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.services.Chronometer
import com.example.runmaster.services.LocationService
import com.example.runmaster.ui.theme.*
import com.example.runmaster.utils.Position
import com.example.runmaster.view_models.OldSessionViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun OldSessionScreen() {
    val positions = OldSessionViewModel.positions
    val totalDistance = OldSessionViewModel.distance
    val timeInMillis = OldSessionViewModel.timeInMillis
    val avgSpeed = OldSessionViewModel.speed
    val numberOfSteps = OldSessionViewModel.numberOfSteps

    Column(
    ){
        // Map displaying positions in a polyline
        Box(modifier = Modifier.weight(2f)){
            if(positions.isEmpty()) {
                positions.add(Position(LocationService.locationPosition.latitude,LocationService.locationPosition.longitude))
            }
            MapWithPolyline(positions = positions)
        }

        // Data displaying distance, number of steps, time, and average speed
        Box(
            modifier = Modifier.weight(1f),
            ){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomDivider(true)
                // Distance and number of steps
                DistanceAndSteps(totalDistance, numberOfSteps)
                TimeAndSpeed(timeInMillis, avgSpeed)
                CustomDivider(true)

            }
        }
    }

}

@Composable
fun MapWithPolyline(positions: List<Position>) {
    val polylinePoints = positions.map { LatLng(it.latitude, it.longitude) }
    // Calculate bounds of the polyline
    val boundsBuilder = LatLngBounds.builder()
    polylinePoints.forEach { boundsBuilder.include(it) }
    val bounds = boundsBuilder.build()

    // Set camera position to fit the bounds of the polyline
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.Builder().target(bounds.center).zoom(15f).build()
    }

    cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(bounds, 150))

    val fixedZoomCameraPosition = rememberCameraPositionState {
        position = CameraPosition.Builder().target(bounds.center).zoom(15f).build()
    }


    GoogleMap(
        //Limits zoom to a maximum
        cameraPositionState = if(cameraPositionState.position.zoom < 15f) {
            cameraPositionState
        } else{
            fixedZoomCameraPosition
        }
    ) {
        // Draw polyline
        Polyline(
            points = polylinePoints,
            color = Purple40
            )
    }
}

@Composable
fun DistanceAndSteps(totalDistance: Double, nSteps: Int) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        OldSessionTile(
            value = String.format("%.2f", totalDistance),
            subtitle = "DISTANCE (km)",
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth()
        )
        OldSessionTile(
            value = nSteps.toString(),
            subtitle = "STEPS",
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth()
        )
    }
}

@Composable
fun TimeAndSpeed(timeInMillis: Long, avgSpeed: Double) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        OldSessionTile(
            value = Chronometer.formatTime(timeInMillis),
            subtitle = "DURATION",
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth()
        )
        OldSessionTile(
            value = String.format("%.2f", avgSpeed),
            subtitle = "AVG SPEED (km/h)",
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth()
        )
    }
}

@Composable
fun OldSessionTile(value: String, subtitle: String,  modifier: Modifier = Modifier) {
    Column(modifier = modifier){
        TextComponent(textValue = value, textSize = 40.sp)
        TextComponent(textValue = subtitle, textSize = CommonConstants.X_NORMAL_FONT_SIZE )
    }
}
