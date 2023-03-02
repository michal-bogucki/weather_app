package com.weatherapplication.feature.cityweather.presentation.view.components.forecast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.weatherapplication.feature.cityweather.presentation.model.ForecastContract
import com.weatherapplication.feature.cityweather.presentation.view.components.ViewError
import com.weatherapplication.feature.cityweather.presentation.view.components.ViewLoading

import com.weatherapplication.feature.cityweather.presentation.view.forecast.ForecastViewModel

@Preview
@Composable
fun ForecastPreview() {
    ForecastViewContent(value = ForecastContract.Loading)
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
            Column {
                Spacer(modifier = Modifier.weight(1f))
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
