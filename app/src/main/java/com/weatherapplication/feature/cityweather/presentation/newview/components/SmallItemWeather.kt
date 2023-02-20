package com.weatherapplication.feature.cityweather.presentation.newview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapplication.R
import com.weatherapplication.feature.cityweather.presentation.newview.components.weather.element2
import com.weatherapplication.feature.cityweather.presentation.newview.components.weather.textColor

class SmallItemWeather

@Preview
@Composable
fun SmallItemWeatherPreview() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)

    ) {
        items(6) { index ->
            Row(Modifier.height(IntrinsicSize.Min)) {
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    SmallItemWeatherContent(title = "Sun", icon = R.drawable.sunny_24, text = "All 12")
                    if (index in (0..2)) {
                        Divider(
                            color = Color(0xFF8C8C9C),
                            modifier = Modifier
                                .fillMaxWidth().padding(start = 8.dp, end = 8.dp)
                                .height(1.dp)
                        )
                    }
                }
                if ((index + 1) % 3 != 0) {
                    Column() {
                        Divider(
                            color = Color(0xFF8C8C9C),
                            modifier = Modifier
                                .fillMaxHeight().padding(top = 8.dp, bottom = 8.dp)
                                .width(1.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SmallItemWeatherItemPreview() {
    SmallItemWeatherContent(title = "Sun", icon = R.drawable.sunny_24, text = "All 12")
}

@Composable
fun SmallItemWeatherView() {
}

@Composable
fun SmallItemWeatherContent(title: String, icon: Int, text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth().padding(16.dp)
            .clickable {
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(element2)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = textColor

        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = textColor

        )
    }
}
