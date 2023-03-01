package com.weatherapplication.feature.cityweather.presentation.view.components.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.weatherapplication.R
import com.weatherapplication.core.background
import com.weatherapplication.core.base.ValueState
import com.weatherapplication.core.element
import com.weatherapplication.core.textColor
import com.weatherapplication.feature.cityweather.presentation.getUnitSymbol
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import com.weatherapplication.feature.cityweather.presentation.view.components.SmallItemWeatherContent
import com.weatherapplication.feature.cityweather.presentation.view.weather.WeatherNewViewModel
import java.time.LocalDateTime

@Preview
@Composable
fun WeatherPreview() {
    WeatherViewContent(value = WeatherContract.Loading)
}

@Composable
fun WeatherView(viewModel: WeatherNewViewModel, navController: NavController) {
    val value by viewModel.state.collectAsState()
    WeatherViewContent(value = value)
}

@Composable
fun WeatherViewContent(value: WeatherContract) {
    when (value) {
        is WeatherContract.Error -> {
            ViewError(error = value.error)
        }
        WeatherContract.Loading -> {
            ViewLoading()
        }
        is WeatherContract.Success -> {
            ViewWeather(value.weatherDisplayable)
        }
    }
}

@Composable
fun ViewLoading() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Please wait...",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,

            ),
        )
    }
}

@Composable
fun ViewError(error: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Error \n $error",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,

            ),
        )
    }
}

@Composable
private fun ViewWeather(weatherDisplayable: WeatherDisplayable) {
    Column(Modifier.background(background)) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_settings_24),
                    contentDescription = null,
                )
            }
        }

        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_round_location_on_24),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                colorFilter = ColorFilter.tint(element),

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = weatherDisplayable.cityName,
                fontSize = 24.sp,
                color = textColor,

            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = weatherDisplayable.date.toString(),
                fontSize = 12.sp,
                color = textColor,

            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .height(54.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val valueTemperature = when (val temperature = weatherDisplayable.temperature) {
                    ValueState.Empty -> "-"
                    is ValueState.Value -> temperature.value.toString()
                } + getUnitSymbol("temperature")
                val valueMaxTemperature = when (val temperature = weatherDisplayable.maxTemperature) {
                    ValueState.Empty -> "-"
                    is ValueState.Value -> temperature.value.toString()
                } + getUnitSymbol("temperature")
                val valueMinTemperature = when (val temperature = weatherDisplayable.minTemperature) {
                    ValueState.Empty -> "-"
                    is ValueState.Value -> temperature.value.toString()
                } + getUnitSymbol("temperature")
                Text(
                    text = valueTemperature,
                    fontSize = 24.sp,
                    color = textColor,

                )
                Text(
                    text = "min $valueMinTemperature/ max $valueMaxTemperature",
                    fontSize = 16.sp,
                    color = textColor,

                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Divider(
                modifier = Modifier
                    .background(element)
                    .fillMaxHeight()
                    .width(2.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val url = when (val url = weatherDisplayable.weatherIcon) {
                    ValueState.Empty -> "-"
                    is ValueState.Value -> "https:" + url.value
                }
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth(),
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            val sunrise = when (val sunrise = weatherDisplayable.sunrise) {
                ValueState.Empty -> "-"
                is ValueState.Value -> sunrise.value
            }

            val sunset = when (val sunset = weatherDisplayable.sunset) {
                ValueState.Empty -> "-"
                is ValueState.Value -> sunset.value
            }
            ComposeCircularProgressBar(
                percentage = 0.80f,
                fillColor = element,
                backgroundColor = Color(android.graphics.Color.parseColor("#90A4AE")),
                strokeWidth = 5.dp,
                sunrise = sunrise,
                sunset = sunset,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
            ) {
                itemsIndexed(getListWidget(weatherDisplayable)) { index, item ->
                    Row(Modifier.height(IntrinsicSize.Min)) {
                        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            SmallItemWeatherContent(title = item.second, icon = item.third, text = item.first)
                            if (index in (0..2)) {
                                Divider(
                                    color = element,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp, end = 8.dp)
                                        .height(1.dp),
                                )
                            }
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
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            val listState = rememberLazyListState()
            LazyRow(
                state = listState,
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
            LaunchedEffect(weatherDisplayable.listHourTemperature) {
                val index = weatherDisplayable.listHourTemperature.indexOfFirst {
                    val hour = when (val hour = it.hour) {
                        ValueState.Empty -> "-"
                        is ValueState.Value -> hour.value
                    }
                    hour.contains(LocalDateTime.now().hour.toString())
                }
                listState.scrollToItem(index)
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
    val uvIndex = when (val uvIndex = weatherDisplayable.uvIndex) {
        ValueState.Empty -> "-"
        is ValueState.Value -> uvIndex.value.toString()
    } + getUnitSymbol("uvIndex")
    val uvIndexT = Triple(uvIndex, "Index UV", R.drawable.sun_wireless)
    val feelLike = when (val feelLike = weatherDisplayable.feelLike) {
        ValueState.Empty -> "-"
        is ValueState.Value -> feelLike.value.toString()
    } + getUnitSymbol("temperature")
    val feelLikeT = Triple(feelLike, "Feellike", R.drawable.sun_thermometer)
    val visibility = when (val visibility = weatherDisplayable.visibility) {
        ValueState.Empty -> "-"
        is ValueState.Value -> visibility.value.toString()
    } + getUnitSymbol("Visibility")
    val visibilityT = Triple(visibility, "Visibility", R.drawable.eye)
    return listOf(windSpeedT, humidityT, precipitationT, uvIndexT, feelLikeT, visibilityT)
}
