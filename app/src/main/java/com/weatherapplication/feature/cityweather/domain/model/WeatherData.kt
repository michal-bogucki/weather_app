package com.weatherapplication.feature.cityweather.domain.model

import com.weatherapplication.core.data.Item
import java.time.LocalDate
import java.time.LocalTime

data class WeatherData(
    val id: Int,
    val cityName: String,
    val date: LocalDate,
    val time: LocalTime,
    val countryName: String,
    val lat: Double,
    val lon: Double,
    val weatherIcon: String,
    val temperature: Int,
    val minTemperature: Double,
    val maxTemperature: Double,
    val conditionWeatherName: String,
    val sunrise: String,
    val sunset: String,
    val windSpeed: Double,
    val humidity: Int,
    val precipitation: Double,
    val uvIndex: Int,
    val feelLike: Double,
    val visibility: Int,
    val listHourTemperature: List<HourTemperature>,
)

data class HourTemperature(
    val hour: String,
    val temperature: Double,
    val weatherIcon: String,
    override val id: Int,
) : Item
