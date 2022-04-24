package com.weatherapplication.feature.cityweather.domain.model

import com.weatherapplication.core.data.Item
import java.time.LocalDateTime

data class WeatherData(
    val id: Int,
    val date : LocalDateTime,
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
    val listHourTemperature: List<HourTemperature>,
)

data class HourTemperature(
    val hour: String,
    val temperature: String,
    val weatherIcon: String, override val id: Int,
) : Item
