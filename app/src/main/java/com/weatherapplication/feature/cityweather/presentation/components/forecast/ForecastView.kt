package com.weatherapplication.feature.cityweather.presentation.components.forecast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.weatherapplication.feature.cityweather.presentation.WeatherViewModel
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract

@Preview
@Composable
fun ForecastPreview() {
    ForecastViewContent(value = WeatherContract.WeatherState())
}

@Composable
fun ForecastView(viewModel: WeatherViewModel, navController: NavController) {
    // val value by viewModel.state.collectAsState()
}

@Composable
fun ForecastViewContent(value: WeatherContract.WeatherState) {
    Column {
        ForecastItemContent()
        Spacer(modifier = Modifier.height(4.dp))
        ForecastItemContent()
        Spacer(modifier = Modifier.height(4.dp))
        ForecastItemContent()
    }
}
