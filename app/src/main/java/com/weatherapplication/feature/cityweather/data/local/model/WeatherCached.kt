package com.weatherapplication.feature.cityweather.data.local.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.presentation.model.HourTemperatureDisplayable
import java.time.LocalDateTime

@Entity(primaryKeys = ["cityName","date"])
data class WeatherCached(
    val cityName: String,
    val date : LocalDateTime,
    val countryName: String,
    val lat: Double,
    val lon: Double,
    val weatherIcon: String,
    val temperature: String,
    val minTemperature: String,
    val maxTemperature: String,
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
) {

}
