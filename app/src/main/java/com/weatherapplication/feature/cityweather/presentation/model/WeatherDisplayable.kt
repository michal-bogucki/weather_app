package com.weatherapplication.feature.cityweather.presentation.model

import com.weatherapplication.core.base.ValueState
import com.weatherapplication.feature.cityweather.domain.model.HourTemperature
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
    val sunrise: LocalTime,
    val sunset: LocalTime,
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
        ValueState.initValueState(weather.weatherIcon),
        ValueState.initValueState(weather.temperature),
        ValueState.initValueState(weather.minTemperature),
        ValueState.initValueState(weather.maxTemperature),
        ValueState.initValueState(weather.conditionWeatherName),
        LocalTime.parse(weather.sunrise, DateTimeFormatter.ofPattern("hh:mm a")),
        LocalTime.parse(weather.sunset, DateTimeFormatter.ofPattern("hh:mm a")),
        ValueState.initValueState(weather.windSpeed),
        ValueState.initValueState(weather.humidity),
        ValueState.initValueState(weather.precipitation),
        ValueState.initValueState(weather.uvIndex),
        ValueState.initValueState(weather.feelLike),
        ValueState.initValueState(weather.visibility),
        weather.listHourTemperature.map { HourTemperatureDisplayable(it) },
    )
}

data class HourTemperatureDisplayable(
    val hour: ValueState<String>,
    val temperature: ValueState<Double>,
    val weatherIcon: ValueState<String>,
) {
    constructor(hour: HourTemperature) : this(
        ValueState.initValueState(hour.hour),
        ValueState.initValueState(hour.temperature),
        ValueState.initValueState(hour.weatherIcon),
    )
}
