package com.weatherapplication.feature.cityweather.presentation.view.components.forecast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.view.forecast.ForecastViewModel

@Preview
@Composable
fun ForecastPreview() {
    ForecastViewContent(value = WeatherContract.Loading)
}

@Composable
fun ForecastView(viewModel: ForecastViewModel, navController: NavController) {
    // val value by viewModel.state.collectAsState()
    ForecastViewContent(value = WeatherContract.Loading)
}

@Composable
fun ForecastViewContent(value: WeatherContract) {
    Column {
        Spacer(modifier = Modifier.weight(1f))
        ForecastItemContent()
        Spacer(modifier = Modifier.weight(1f))
        ForecastItemContent()
        Spacer(modifier = Modifier.weight(1f))
        ForecastItemContent()
        Spacer(modifier = Modifier.weight(1f))
    }
}
