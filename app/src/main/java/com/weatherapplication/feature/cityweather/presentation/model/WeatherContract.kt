package com.weatherapplication.feature.cityweather.presentation.model

sealed interface WeatherContract {

    data class Success(val weatherDisplayable: WeatherDisplayable) : WeatherContract
    data class Error(val error: String) : WeatherContract
    object Loading : WeatherContract
}
