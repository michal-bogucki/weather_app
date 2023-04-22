package com.weatherapplication.feature.cityweather.presentation.view.weather.compose

import android.Manifest
import android.app.TimePickerDialog
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.weatherapplication.R
import com.weatherapplication.core.alarm.ExactAlarms
import com.weatherapplication.core.background
import com.weatherapplication.core.base.ValueState
import com.weatherapplication.core.base.getValue
import com.weatherapplication.core.element
import com.weatherapplication.core.textColor
import com.weatherapplication.feature.choosesearchlocationorgetfromgps.LocationOptionButton
import com.weatherapplication.feature.cityweather.presentation.TEMPERATURE
import com.weatherapplication.feature.cityweather.presentation.getListWidget
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import com.weatherapplication.feature.cityweather.presentation.view.components.ItemHourTemperature
import com.weatherapplication.feature.cityweather.presentation.view.components.SmallItemWeatherContent
import com.weatherapplication.feature.cityweather.presentation.view.components.ViewError
import com.weatherapplication.feature.cityweather.presentation.view.components.ViewLoading
import com.weatherapplication.feature.cityweather.presentation.view.components.weather.ComposeCircularProgressBar
import com.weatherapplication.feature.cityweather.presentation.view.weather.WeatherNewFragmentDirections
import com.weatherapplication.feature.cityweather.presentation.view.weather.WeatherNewViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Composable
fun WeatherView(
    viewModel: WeatherNewViewModel,
    navController: NavController,
    exactAlarms: ExactAlarms
) {
    val value by viewModel.state.collectAsState()
    WeatherViewContent(
        value = value,
        {
            navController.navigate(
                WeatherNewFragmentDirections.actionWeatherNewFragmentToSearchCityFragment(),
                NavOptions.Builder()
                    .setPopUpTo(R.id.weatherNewFragment, true)
                    .setLaunchSingleTop(true)
                    .build()
            )
        },
        {
            navController.navigate(
                WeatherNewFragmentDirections.actionWeatherNewFragmentToForecastFragment(
                    viewModel.cityId
                )
            )
        },
        {
            navController.navigate(
                WeatherNewFragmentDirections.actionWeatherNewFragmentToAirQualityFragment(
                    viewModel.cityId
                )
            )
        },
        viewModel::getData,
        exactAlarms::scheduleExactAlarm
    )
}

@Composable
fun WeatherViewContent(
    value: WeatherContract,
    openSearchCity: () -> Unit = {},
    openForecastFragment: () -> Unit = {},
    openAirQualityFragment: () -> Unit = {},
    refreshData: () -> Unit = {},
    addAlarm: (Int, Int) -> Unit = { hour: Int, minute: Int -> {} }
) {
    when (value) {
        is WeatherContract.Success -> {
            ViewWeather(
                value.weatherDisplayable,
                openSearchCity,
                openForecastFragment,
                openAirQualityFragment,
                addAlarm
            )
        }

        is WeatherContract.Error -> {
            ViewError(error = value.error, refreshData)
        }

        WeatherContract.Loading -> {
            ViewLoading()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ViewWeather(
    weatherDisplayable: WeatherDisplayable,
    openSearchCity: () -> Unit,
    openForecastFragment: () -> Unit,
    openAirQualityFragment: () -> Unit,
    addAlarm: (Int, Int) -> Unit
) {
    val locationPermissionsState = rememberPermissionState(
        Manifest.permission.POST_NOTIFICATIONS
    )
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.city2))
    val progress by animateLottieCompositionAsState(composition)
    val listState = rememberLazyListState()
    Column(Modifier.background(background)) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(86.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current
            val calendar = Calendar.getInstance()
            val hour = calendar[Calendar.HOUR_OF_DAY]
            val minute = calendar[Calendar.MINUTE]
            val timePicker = TimePickerDialog(
                context,
                { _, selectedHour: Int, selectedMinute: Int ->
                    addAlarm(selectedHour, selectedMinute)
                },
                hour,
                minute,
                false
            )
            Column(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        if (locationPermissionsState.status.isGranted) {
                            timePicker.show()
                        } else {
                            locationPermissionsState.launchPermissionRequest()
                        }
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_add_alarm_24),
                    contentDescription = null
                )
            }
            LottieAnimation(
                modifier = Modifier
                    .height(86.dp)
                    .weight(1f),
                composition = composition,
                progress = { progress }
            )
            Column(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        openSearchCity()
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_round_search_24),
                    contentDescription = null
                )
            }
        }

        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_round_location_on_24),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                colorFilter = ColorFilter.tint(element)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = weatherDisplayable.cityName,
                fontSize = 24.sp,
                color = textColor

            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weatherDisplayable.date.toString(),
                fontSize = 12.sp,
                color = textColor

            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .height(72.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                val valueTemperature = getValue(weatherDisplayable.temperature, TEMPERATURE)
                val valueMaxTemperature = getValue(weatherDisplayable.maxTemperature, TEMPERATURE)
                val valueMinTemperature = getValue(weatherDisplayable.minTemperature, TEMPERATURE)
                Text(
                    text = valueTemperature,
                    fontSize = 36.sp,
                    color = textColor

                )
                Text(
                    text = "$valueMinTemperature/$valueMaxTemperature",
                    fontSize = 16.sp,
                    color = textColor

                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val url = "https:" + getValue(weatherDisplayable.weatherIcon)
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .height(72.dp)
                        .fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val sunrise = weatherDisplayable.sunrise.toString()
            val sunset = weatherDisplayable.sunset.toString()
            ComposeCircularProgressBar(
                fillColor = element,
                backgroundColor = Color(android.graphics.Color.parseColor("#90A4AE")),
                strokeWidth = 2.dp,
                sunrise = sunrise,
                sunset = sunset
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                itemsIndexed(getListWidget(weatherDisplayable)) { index, item ->
                    Row(Modifier.height(IntrinsicSize.Min)) {
                        Column(
                            Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SmallItemWeatherContent(
                                title = item.second,
                                icon = item.third,
                                text = item.first
                            )
                            if (index in (0..2)) {
                                Divider(
                                    color = element,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp, end = 8.dp)
                                        .height(1.dp)
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
                                        .width(1.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(weatherDisplayable.listHourTemperature) {
                    val temperature = getValue(it.temperature, TEMPERATURE)
                    val hour = getValue(it.hour)
                    val url = "https:" + getValue(it.weatherIcon)
                    ItemHourTemperature(title = hour, icon = url, text = temperature)
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
        Spacer(modifier = Modifier.height(16.dp))
        LocationOptionButton(
            label = stringResource(R.string.next_day),
            onClick = { openForecastFragment() },
            modifier = Modifier.padding(start = 168.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LocationOptionButton(
            label = stringResource(R.string.air_quality),
            onClick = { openAirQualityFragment() },
            modifier = Modifier.padding(start = 168.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

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
                listOf()
            )
        )

    )
}
