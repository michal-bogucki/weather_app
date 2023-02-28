package com.weatherapplication.feature.cityweather.presentation.view.components.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.weatherapplication.core.base.ValueState
import com.weatherapplication.feature.cityweather.presentation.getUnitSymbol
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import com.weatherapplication.feature.cityweather.presentation.view.components.SmallItemWeatherContent
import com.weatherapplication.feature.cityweather.presentation.view.weather.WeatherNewViewModel
import java.time.LocalDateTime

// val background = Color(0xFF24293E)
// val textColor = Color(0xFFF4F5FC)
// val element = Color(0xFF8EBBFF)
// val element2 = Color(0xFFCCCCCC)

val background = Color(0xFF171923)
val textColor = Color(0xFFFFFFFF)
val element = Color(0xFFF79E1B)
val element2 = Color(0xFFF79E1B)

// val background = Color(0xFF191D2B)
// val textColor = Color(0xFFF3E1D3)
// val element = Color(0xFFFB7023)
// val element2 = Color(0xFFFB7023)

// val background = Color(0xFF292A31)
// val textColor = Color(0xFFFFFFFF)
// val element = Color(0xFFFFB700)
// val element2 = Color(0xFF4871FF)

// val background = Color(0xFF5D5E67)
// val textColor = Color(0xFFFFFFFF)
// val element = Color(0xFFDDD8AE)
// val element2 = Color(0xFFDDD8AE)
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
            Column(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_round_menu_24),
                    contentDescription = null,

                )
            }

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
                    painter = painterResource(id = R.drawable.ic_round_search_24),
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
                val valueMinTemperature = when (val temperature = weatherDisplayable.maxTemperature) {
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
                    colorFilter = ColorFilter.tint(textColor),
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
                items(6) { index ->
                    Row(Modifier.height(IntrinsicSize.Min)) {
                        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            SmallItemWeatherContent(title = "Sun", icon = R.drawable.sunny_24, text = "All 12")
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
