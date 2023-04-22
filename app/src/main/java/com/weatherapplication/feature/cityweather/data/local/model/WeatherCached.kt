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
    val listHourTemperature: List<HourTemperatureCached>
) {
    fun toWeatherData() = WeatherData(
        cityName = cityName,
        date = date,
        lastUpdate = lastUpdate,
        countryName = countryName,
        lat = lat,
        lon = lon,
        weatherIcon = weatherIcon,
        temperature = temperature,
        minTemperature = minTemperature,
        maxTemperature = maxTemperature,
        conditionWeatherName = conditionWeatherName,
        sunrise = sunrise,
        sunset = sunset,
        windSpeed = windSpeed,
        humidity = humidity,
        precipitation = precipitation,
        uvIndex = uvIndex,
        feelLike = feelLike,
        visibility = visibility,
        listHourTemperature = listHourTemperature.map { it.toHourTemperature() }
    )
}

fun createWeatherCached(weather: WeatherData): WeatherCached {
    return WeatherCached(
        cityName = weather.cityName,
        date = weather.date,
        lastUpdate = weather.lastUpdate,
        countryName = weather.countryName,
        lat = weather.lat,
        lon = weather.lon,
        weatherIcon = weather.weatherIcon,
        temperature = weather.temperature,
        minTemperature = weather.minTemperature,
        maxTemperature = weather.maxTemperature,
        conditionWeatherName = weather.conditionWeatherName,
        sunrise = weather.sunrise,
        sunset = weather.sunset,
        windSpeed = weather.windSpeed,
        humidity = weather.humidity,
        precipitation = weather.precipitation,
        uvIndex = weather.uvIndex,
        feelLike = weather.feelLike,
        visibility = weather.visibility,
        listHourTemperature = weather.listHourTemperature.map {
            HourTemperatureCached(it)
        }
    )
}

data class HourTemperatureCached(
    val hour: String?,
    val temperature: Double?,
    val weatherIcon: String?
) {
    constructor(hourTemperature: HourTemperature) : this(
        hour = hourTemperature.hour,
        temperature = hourTemperature.temperature,
        weatherIcon = hourTemperature.weatherIcon
    )

    fun toHourTemperature() = HourTemperature(
        hour = hour,
        temperature = temperature,
        weatherIcon = weatherIcon
    )
}
