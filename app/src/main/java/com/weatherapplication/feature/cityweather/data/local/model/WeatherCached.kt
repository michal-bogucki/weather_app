package com.weatherapplication.feature.cityweather.data.local.model

import androidx.room.Entity
import androidx.room.TypeConverters
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
    constructor(weather: WeatherData) : this(
        weather.cityName,
        weather.date,
        weather.lastUpdate,
        weather.countryName,
        weather.lat,
        weather.lon,
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
        weather.listHourTemperature.map {
            HourTemperatureCached(it)
        },
    )

    fun toWeatherData() = WeatherData(
        cityName,
        date,
        lastUpdate,
        countryName,
        lat,
        lon,
        weatherIcon,
        temperature,
        minTemperature,
        maxTemperature,
        conditionWeatherName,
        sunrise,
        sunset,
        windSpeed,
        humidity,
        precipitation,
        uvIndex,
        feelLike,
        visibility,
        listHourTemperature.map { it.toHourTemperature() },

    )
}

data class HourTemperatureCached(
    val hour: String?,
    val temperature: Double?,
    val weatherIcon: String?,
) {
    constructor(hourTemperature: HourTemperature) : this(
        hourTemperature.hour,
        hourTemperature.temperature,
        hourTemperature.weatherIcon,
    )

    fun toHourTemperature() = HourTemperature(
        hour,
        temperature,
        weatherIcon,
    )
}
