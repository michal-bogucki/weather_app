package com.weatherapplication.feature.cityweather.presentation.model

import com.weatherapplication.core.data.NavigationEvent
import com.weatherapplication.core.data.ViewEvent
import com.weatherapplication.core.data.ViewState

class WeatherContract {
    data class WeatherState(
        val isLoading: Boolean = false,
        val error: String = "",
        val listWeatherData: List<WeatherDisplayable>
    ) : ViewState

    sealed class WeatherEvent : ViewEvent {
        data class ShowOtherDate(val id: Int) : WeatherEvent()
    }

    sealed class NavigationWeatherEvent : NavigationEvent {
        object ShowOtherCity : NavigationWeatherEvent()
        object OpenMenu : NavigationWeatherEvent()
    }
}