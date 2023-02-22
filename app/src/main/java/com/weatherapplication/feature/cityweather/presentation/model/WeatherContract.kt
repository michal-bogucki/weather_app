package com.weatherapplication.feature.cityweather.presentation.model

sealed interface WeatherContract {

    data class Success(val weatherDisplayable: WeatherDisplayable) : WeatherContract
    data class Error(val error: String) : WeatherContract
    object Loading : WeatherContract

//    data class WeatherState(
//        val isLoading: Boolean = false,
//        val error: String = "",
//        val weatherDisplayable: WeatherDisplayable? = null,
//    ) : ViewState
//
//    companion object {
//        val Empty = WeatherState()
//    }
}
