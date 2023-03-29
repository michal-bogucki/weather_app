package com.weatherapplication.feature.cityweather.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class WeatherData(
    val cityName: String,
    val date: LocalDate,
    val lastUpdate: LocalDateTime,
    val countryName: String,
    val lat: Double,
    val lon: Double,
    val weatherIcon: String?,
    val temperature: Double?,
    val minTemperature: Double?,
    val maxTemperature: Double?,
    val conditionWeatherName: String?,
    val sunrise: String?,
    val sunset: String?,
    val windSpeed: Double?,
    val humidity: Int?,
    val precipitation: Double?,
    val uvIndex: Double?,
    val feelLike: Double?,
    val visibility: Double?,
    val listHourTemperature: List<HourTemperature>,
)

data class HourTemperature(
    val hour: String?,
    val temperature: Double?,
    val weatherIcon: String?,
)
