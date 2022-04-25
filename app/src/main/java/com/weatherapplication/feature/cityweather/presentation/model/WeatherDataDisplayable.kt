package com.weatherapplication.feature.cityweather.presentation.model

import com.weatherapplication.core.data.Item
import java.time.LocalDateTime

data class WeatherDisplayable(
    val id: Int,
    val date: LocalDateTime,
    val weatherIcon: String,
    val temperature: String,
    val minTemperature: String,
    val maxTemperature: String,
    val cityName: String,
    val dateFull: String,
    val datePart: String,
    val conditionWeatherName: String,
    val sunrise: String,
    val sunset: String,
    val windSpeed: String,
    val humidity: String,
    val precipitation: String,
    val uvIndex: String,
    val feelLike: String,
    val visibility: String,
    val listHourTemperature: List<HourTemperatureDisplayable>,
)

data class HourTemperatureDisplayable(
    val hour: String,
    val temperature: String,
    val weatherIcon: String,
    override val id: Int,
) : Item