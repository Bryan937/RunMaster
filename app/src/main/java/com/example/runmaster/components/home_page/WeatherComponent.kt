package com.example.runmaster.components.home_page
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage
import com.example.runmaster.constants.CommonConstants.LARGE_PADDING
import com.example.runmaster.services.WeatherService
import com.example.runmaster.utils.WeatherIconConverter


@Composable
fun WeatherCardComponent() {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    val data = WeatherService.weatherResponse.value
    val width = (3 * screenWidthDp) / 4
    val height = screenHeightDp / 6
    if (data != null) {
        Card(
            modifier = Modifier
                .width(width)
                .height(height)
                .padding(LARGE_PADDING)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = data.name, fontSize = 24.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        AsyncImage(
                            model = data.weather[0].let { WeatherIconConverter.convertIcon(it.icon) },
                            contentDescription = "icon"
                        )

                    }
                    Text(text = "${(data.main.temp).toInt()} °C ", fontSize = 28.sp)
                }
                Text(text = "(${data.weather[0].description})", fontSize = 14.sp)
                Text(text = "feels like: ${(data.main.feels_like).toInt()} °C ", fontSize = 18.sp)
            }
        }
    }
}