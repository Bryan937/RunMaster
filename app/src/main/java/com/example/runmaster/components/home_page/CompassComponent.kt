package com.example.runmaster.components.home_page

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import com.example.runmaster.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.runmaster.services.SensorEventListener
import com.example.runmaster.ui.theme.PurpleGrey40

@Composable
fun Compass() {
    var compassRotation by remember { mutableStateOf(0f) }

    DisposableEffect(Unit) {
        SensorEventListener.onCompassOrientationChanged = { newRotation ->
            compassRotation = newRotation
        }
        onDispose {
            SensorEventListener.onCompassOrientationChanged = null
        }
    }
    Box(
        modifier = Modifier
            .size(75.dp)
            .clip(CircleShape)
            .background(PurpleGrey40)
            .rotate(compassRotation),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Image(modifier = Modifier.size(25.dp), painter = painterResource(id = R.drawable.red_arrow), contentDescription = "compass")
            Text(text = "N", fontSize = 25.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Preview
@Composable
fun CompassPreview(){
    Compass()
}

