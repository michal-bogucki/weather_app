package com.weatherapplication.feature.cityweather.presentation.model

import com.weatherapplication.core.data.Item
import com.weatherapplication.feature.cityweather.domain.model.HourTemperature
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherDisplayable(
    override val id: Int,
    val cityName: String,
    val date: LocalDate,
    val lastUpdate: LocalDateTime,
    val countryName: String,
    val weatherIcon: String,
    val temperature: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val conditionWeatherName: String,
    val sunrise: String,
    val sunset: String,
    val windSpeed: Double,
    val humidity: Int,
    val precipitation: Double,
    val uvIndex: Double,
    val feelLike: Double,
    val visibility: Double,
    val listHourTemperature: List<HourTemperatureDisplayable>,
) : Item {
    constructor(weather: WeatherData) : this(
        weather.date.hashCode(),
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
        weather.listHourTemperature.map { HourTemperatureDisplayable(it, weather.date.hashCode()) }
    )
}

data class HourTemperatureDisplayable(
    val hour: String,
    val temperature: Double,
    val weatherIcon: String,
    override val id: Int,
) : Item {
    constructor(hour: HourTemperature, id: Int) : this(
        hour.hour,
        hour.temperature,
        hour.weatherIcon,
        id
    )
}
