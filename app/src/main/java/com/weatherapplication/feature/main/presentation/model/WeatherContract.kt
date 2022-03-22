package com.weatherapplication.feature.main.presentation.model

import com.weatherapplication.core.data.ViewEvent
import com.weatherapplication.core.data.ViewState

class WeatherContract {
    data class WeatherState(
        val isLoading:Boolean = false,
        val error: String = "",
        val weatherData : WeatherDataDisplayable
    ):ViewState
    sealed class WeatherEvent:ViewEvent{

    }
}