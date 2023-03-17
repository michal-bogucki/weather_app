package com.weatherapplication.feature.cityweather.presentation.view.components.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.weatherapplication.core.background
import com.weatherapplication.core.base.ValueState
import com.weatherapplication.core.textColor
import com.weatherapplication.feature.cityweather.presentation.model.ForecastContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import com.weatherapplication.feature.cityweather.presentation.view.components.ViewError
import com.weatherapplication.feature.cityweather.presentation.view.components.ViewLoading
import com.weatherapplication.feature.cityweather.presentation.view.forecast.ForecastViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Preview
@Composable
fun ForecastPreview() {
    ForecastViewContent(
        value = ForecastContract.Success(
            listOf(
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

    )

}

@Composable
fun ForecastView(viewModel: ForecastViewModel, navController: NavController) {
    val value by viewModel.state.collectAsState()
    ForecastViewContent(value = value)
}

@Composable
fun ForecastViewContent(value: ForecastContract) {
    when (value) {
        is ForecastContract.Error -> {
            ViewError(error = value.error)
        }
        ForecastContract.Loading -> {
            ViewLoading()
        }
        is ForecastContract.Success -> {
            val weatherDisplayable = value.weatherDisplayable
            Column(
                modifier = Modifier
                    .background(background)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Next day",
                    fontSize = 18.sp,
                    color = textColor,
                )
                Spacer(modifier = Modifier.height(32.dp))
                LazyColumn {
                    items(weatherDisplayable) {
                        ForecastItemContent(it)
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
