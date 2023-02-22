package com.weatherapplication.feature.cityweather.domain.model

import com.weatherapplication.core.base.ValueState
import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherData(
    val id: Int,
    val cityName: String,
    val date: LocalDate,
    val lastUpdate: LocalDateTime,
    val countryName: String,
    val lat: Double,
    val lon: Double,
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
    val listHourTemperature: List<HourTemperature>,
)

data class HourTemperature(
    val hour: ValueState<String>,
    val temperature: ValueState<Double>,
    val weatherIcon: ValueState<String>,
)
