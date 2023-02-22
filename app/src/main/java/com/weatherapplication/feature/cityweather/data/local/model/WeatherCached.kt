package com.weatherapplication.feature.cityweather.data.local.model

import androidx.room.Entity
import androidx.room.TypeConverters
import com.weatherapplication.core.base.ValueState

import com.weatherapplication.core.database.Converters
import com.weatherapplication.feature.cityweather.domain.model.HourTemperature
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(primaryKeys = ["cityName", "date"])
@TypeConverters(Converters::class)
data class WeatherCached(
    val cityName: String,
    val date: LocalDate = LocalDate.now(),
    val lastUpdate: LocalDateTime = LocalDateTime.now(),
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
    val listHourTemperature: List<HourTemperatureCached>,
) {
    fun toWeatherData() = WeatherData(
        cityName.toCharArray().sumOf { it.code },
        cityName,
        date,
        lastUpdate,
        countryName,
        lat,
        lon,
        ValueState.initValueState(weatherIcon),
        ValueState.initValueState(temperature),
        ValueState.initValueState(minTemperature),
        ValueState.initValueState(maxTemperature),
        ValueState.initValueState(conditionWeatherName),
        ValueState.initValueState(sunrise),
        ValueState.initValueState(sunset),
        ValueState.initValueState(windSpeed),
        ValueState.initValueState(humidity),
        ValueState.initValueState(precipitation),
        ValueState.initValueState(uvIndex),
        ValueState.initValueState(feelLike),
        ValueState.initValueState(visibility),
        listHourTemperature.map { it.toHourTemperature() },

    )
}

data class HourTemperatureCached(
    val hour: String?,
    val temperature: Double?,
    val weatherIcon: String?,
) {
    fun toHourTemperature() = HourTemperature(
        ValueState.initValueState(hour),
        ValueState.initValueState(temperature),
        ValueState.initValueState(weatherIcon),
    )
}
