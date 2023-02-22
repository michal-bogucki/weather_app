package com.weatherapplication.feature.cityweather.presentation.model

import com.weatherapplication.core.base.ValueState
import com.weatherapplication.feature.cityweather.domain.model.HourTemperature
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherDisplayable(
    val cityName: String,
    val date: LocalDate,
    val lastUpdate: LocalDateTime,
    val countryName: String,
    val weatherIcon: ValueState<String>,
    val temperature: ValueState<Double>,
    val minTemperature: ValueState<Double>,
    val maxTemperature: ValueState<Double>,
    val conditionWeatherName: ValueState<String>,
    val sunrise: ValueState<String>,
    val sunset: ValueState<String>,
    val windSpeed: ValueState<Double>,
    val humidity: ValueState<Int>,
    val precipitation: ValueState<Double>,
    val uvIndex: ValueState<Double>,
    val feelLike: ValueState<Double>,
    val visibility: ValueState<Double>,
    val listHourTemperature: List<HourTemperatureDisplayable>,
) {
    constructor(weather: WeatherData) : this(
        weather.cityName,
        weather.date,
        weather.lastUpdate,
        weather.countryName,
        weather.weatherIcon,
        weather.temperature,
        weather.minTemperature,
        weather.maxTemperature,
        weather.conditionWeatherName,
        weather.sunrise,
        weather.sunset,
        weather.windSpeed,
        weather.humidity,
        weather.precipitation,
        weather.uvIndex,
        weather.feelLike,
        weather.visibility,
        weather.listHourTemperature.map { HourTemperatureDisplayable(it) },
    )
}

data class HourTemperatureDisplayable(
    val hour: ValueState<String>,
    val temperature: ValueState<Double>,
    val weatherIcon: ValueState<String>,
) {
    constructor(hour: HourTemperature) : this(
        hour.hour,
        hour.temperature,
        hour.weatherIcon,
    )
}
