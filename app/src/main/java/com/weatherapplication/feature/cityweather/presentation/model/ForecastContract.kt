package com.weatherapplication.feature.cityweather.presentation.model

sealed interface ForecastContract {

    data class Success(val weatherDisplayable: List<WeatherDisplayable>) : ForecastContract
    data class Error(val error: String) : ForecastContract
    object Loading : ForecastContract
}
