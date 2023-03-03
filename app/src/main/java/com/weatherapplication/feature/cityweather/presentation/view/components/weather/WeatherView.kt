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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.weatherapplication.R
import com.weatherapplication.core.background
import com.weatherapplication.core.base.ValueState
import com.weatherapplication.core.base.getValue
import com.weatherapplication.core.element
import com.weatherapplication.core.textColor
import com.weatherapplication.feature.cityweather.presentation.TEMPERATURE
import com.weatherapplication.feature.cityweather.presentation.getListWidget
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import com.weatherapplication.feature.cityweather.presentation.view.components.SmallItemWeatherContent
import com.weatherapplication.feature.cityweather.presentation.view.components.ViewError
import com.weatherapplication.feature.cityweather.presentation.view.components.ViewLoading
import com.weatherapplication.feature.cityweather.presentation.view.weather.WeatherNewViewModel
import java.lang.Math.abs
import java.lang.Math.max
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit.MINUTES

@Preview
@Composable
fun WeatherPreview() {
    WeatherViewContent(
        value = WeatherContract.Success(
            WeatherDisplayable(
                "Kielce",
                LocalDate.now(),
                LocalDateTime.now(),
                "PL",
                ValueState.initValueState(""),
                ValueState.initValueState(3.3),
                ValueState.initValueState(3.3),
                ValueState.initValueState(3.3),
                ValueState.initValueState(""),
                LocalTime.now(),
                LocalTime.now(),
                ValueState.initValueState(3.5),
                ValueState.initValueState(3),
                ValueState.initValueState(3.5),
                ValueState.initValueState(3.5),
                ValueState.initValueState(3.5),
                ValueState.initValueState(3.5),
                listOf(),
            ),
        ),
    )
}

@Composable
fun WeatherView(viewModel: WeatherNewViewModel) {
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
private fun ViewWeather(weatherDisplayable: WeatherDisplayable) {
    Column(Modifier.background(background)) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(86.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(48.dp))
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.city2))
            val progress by animateLottieCompositionAsState(composition)
            LottieAnimation(
                modifier = Modifier
                    .height(86.dp)
                    .weight(1f),
                composition = composition,
                progress = { progress },
            )
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
                val valueTemperature = getValue(weatherDisplayable.temperature, TEMPERATURE)
                val valueMaxTemperature = getValue(weatherDisplayable.maxTemperature, TEMPERATURE)
                val valueMinTemperature = getValue(weatherDisplayable.minTemperature, TEMPERATURE)
                Text(
                    text = valueTemperature,
                    fontSize = 24.sp,
                    color = textColor,

                )
                Text(
                    text = "${stringResource(id = R.string.min)} $valueMinTemperature/ ${stringResource(id = R.string.max)} $valueMaxTemperature",
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
                val url = "https:" + getValue(weatherDisplayable.weatherIcon)
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
            val sunrise = weatherDisplayable.sunrise.toString()
            val sunset = weatherDisplayable.sunset.toString()
            val a = abs(MINUTES.between(weatherDisplayable.sunset, weatherDisplayable.sunrise))
            val b = abs(MINUTES.between(LocalTime.now(), weatherDisplayable.sunrise))
            val c = max((b / (a.toDouble())).toFloat(), 1f)
            ComposeCircularProgressBar(
                percentage = c,
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
                    val temperature = getValue(it.temperature, TEMPERATURE)
                    val hour = getValue(it.hour)
                    val url = "https:" + getValue(it.weatherIcon)
                    SmallItemWeatherContent(title = hour, icon = url, text = temperature)
                }
            }
            LaunchedEffect(weatherDisplayable.listHourTemperature) {
                val index = weatherDisplayable.listHourTemperature.indexOfFirst {
                    val hour = getValue(it.hour)
                    hour.contains(LocalDateTime.now().hour.toString())
                }
                listState.scrollToItem(index)
            }
        }
    }
}
