package com.weatherapplication.feature.cityweather.presentation.newview.components.forecast

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapplication.R
import com.weatherapplication.feature.cityweather.presentation.newview.components.SmallItemWeatherContent
import com.weatherapplication.feature.cityweather.presentation.newview.components.weather.background
import com.weatherapplication.feature.cityweather.presentation.newview.components.weather.element
import com.weatherapplication.feature.cityweather.presentation.newview.components.weather.textColor

@Preview
@Composable
fun ForecastItemPreview() {
    ForecastItemContent()
}

@Composable
fun ForecastItem() {
}

@Composable
fun ForecastItemContent() {
    // pokazać kolejnośc border padding
    Card(
        elevation = 20.dp,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, element),
        modifier = Modifier.padding(8.dp)
    ) {
        Column(Modifier.background(background).padding(4.dp)) {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_round_location_on_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp),
                    colorFilter = ColorFilter.tint(element)

                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Kraków",
                    fontSize = 12.sp,
                    color = textColor

                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sat, 3 Aug",
                    fontSize = 12.sp,
                    color = textColor

                )
            }
            Row {
                LazyVerticalGrid(
                    modifier = Modifier.weight(2f).height(80.dp),
                    columns = GridCells.Fixed(3)
                ) {
                    items(3) { index ->
                        Row(Modifier.height(IntrinsicSize.Min)) {
                            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                SmallItemWeatherContent(title = "Sun", icon = R.drawable.sunny_24, text = "All 12")
                            }
                            if ((index + 1) % 3 != 0) {
                                Column {
                                    Divider(
                                        color = element,
                                        modifier = Modifier
                                            .fillMaxHeight().padding(top = 8.dp, bottom = 8.dp)
                                            .width(1.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                Column(Modifier.weight(1f).height(80.dp), verticalArrangement = Arrangement.Center) {
                    Row(
                        Modifier.height(54.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "3°",
                            fontSize = 24.sp,
                            color = textColor

                        )
                        Image(
                            painter = painterResource(id = R.drawable.weather_icon),
                            contentDescription = null,
                            modifier = Modifier.height(56.dp),
                            colorFilter = ColorFilter.tint(textColor)
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "min 3°/ max 3°",
                            fontSize = 16.sp,
                            color = textColor

                        )
                    }
                }
            }
            Column {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(14) {
                        SmallItemWeatherContent(title = "Sun", icon = R.drawable.sunny_24, text = "All 12")
                    }
                }
            }
        }
    }
}
