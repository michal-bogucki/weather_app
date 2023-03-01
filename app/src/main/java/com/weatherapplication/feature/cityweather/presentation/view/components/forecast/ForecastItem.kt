package com.weatherapplication.feature.cityweather.presentation.view.components.forecast

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
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
import coil.compose.AsyncImage
import com.weatherapplication.R
import com.weatherapplication.core.background
import com.weatherapplication.core.base.ValueState
import com.weatherapplication.core.element
import com.weatherapplication.core.textColor
import com.weatherapplication.feature.cityweather.presentation.getUnitSymbol
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import com.weatherapplication.feature.cityweather.presentation.view.components.SmallItemWeatherContent

@Preview
@Composable
fun ForecastItemPreview() {
}

@Composable
fun ForecastItem() {
}

@Composable
fun ForecastItemContent(weatherDisplayable: WeatherDisplayable) {
    // pokazać kolejnośc border padding
    Card(
        elevation = 20.dp,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, element),
        modifier = Modifier.padding(8.dp),
    ) {
        Column(
            Modifier
                .background(background)
                .padding(4.dp),
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_round_location_on_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp),
                    colorFilter = ColorFilter.tint(element),

                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = weatherDisplayable.cityName,
                    fontSize = 12.sp,
                    color = textColor,

                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = weatherDisplayable.date.toString(),
                    fontSize = 12.sp,
                    color = textColor,

                )
            }
            Row {
                LazyVerticalGrid(
                    modifier = Modifier
                        .weight(4f)
                        .height(92.dp),
                    columns = GridCells.Fixed(3),
                ) {
                    itemsIndexed(getListWidget(weatherDisplayable)) { index, item ->
                        Row(Modifier.height(IntrinsicSize.Min)) {
                            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                SmallItemWeatherContent(title = item.second, icon = item.third, text = item.first)
                            }
                            if ((index + 1) % 3 != 0) {
                                Column {
                                    Divider(
                                        color = element,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(top = 8.dp, bottom = 8.dp)
                                            .width(1.dp),
                                    )
                                }
                            }
                        }
                    }
                }
                Column(
                    Modifier
                        .weight(1f)
                        .height(92.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        Modifier
                            .height(54.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val icon = weatherDisplayable.listHourTemperature.map {
                            when (val icon = it.weatherIcon) {
                                ValueState.Empty -> "-"
                                is ValueState.Value -> icon.value
                            }
                        }.groupingBy { it }.eachCount().maxBy { it.value }
                        AsyncImage(
                            model = "https:" + icon.key,
                            contentDescription = null,
                            modifier = Modifier.height(56.dp),
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        val valueMaxTemperature = when (val temperature = weatherDisplayable.maxTemperature) {
                            ValueState.Empty -> "-"
                            is ValueState.Value -> temperature.value.toString()
                        } + getUnitSymbol("temperature")
                        val valueMinTemperature = when (val temperature = weatherDisplayable.minTemperature) {
                            ValueState.Empty -> "-"
                            is ValueState.Value -> temperature.value.toString()
                        } + getUnitSymbol("temperature")
                        Text(
                            text = "min $valueMinTemperature\nmax $valueMaxTemperature",
                            fontSize = 12.sp,
                            color = textColor,

                        )
                    }
                }
            }
            Column {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(weatherDisplayable.listHourTemperature) {
                        val temperature = when (val temperature = it.temperature) {
                            ValueState.Empty -> "-"
                            is ValueState.Value -> temperature.value.toString()
                        } + getUnitSymbol("temperature")
                        val hour = when (val hour = it.hour) {
                            ValueState.Empty -> "-"
                            is ValueState.Value -> hour.value
                        }
                        val url = when (val url = it.weatherIcon) {
                            ValueState.Empty -> "-"
                            is ValueState.Value -> "https:" + url.value
                        }
                        SmallItemWeatherContent(title = hour, icon = url, text = temperature)
                    }
                }
            }
        }
    }
}

fun getListWidget(weatherDisplayable: WeatherDisplayable): List<Triple<String, String, Int>> {
    val windSpeed = when (val windSpeed = weatherDisplayable.windSpeed) {
        ValueState.Empty -> "-"
        is ValueState.Value -> windSpeed.value.toString()
    } + getUnitSymbol("windSpeed")
    val windSpeedT = Triple(windSpeed, "Wind", R.drawable.weather_windy)
    val humidity = when (val humidity = weatherDisplayable.humidity) {
        ValueState.Empty -> "-"
        is ValueState.Value -> humidity.value.toString()
    } + getUnitSymbol("humidity")
    val humidityT = Triple(humidity, "Humidity", R.drawable.water_percent)
    val precipitation = when (val precipitation = weatherDisplayable.precipitation) {
        ValueState.Empty -> "-"
        is ValueState.Value -> precipitation.value.toString()
    } + getUnitSymbol("precipitation")
    val precipitationT = Triple(precipitation, "Precipitation", R.drawable.weather_pouring)

    return listOf(windSpeedT, humidityT, precipitationT)
}
