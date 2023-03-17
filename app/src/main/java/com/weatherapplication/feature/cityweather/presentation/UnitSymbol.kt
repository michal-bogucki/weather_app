package com.weatherapplication.feature.cityweather.presentation

import com.weatherapplication.R
import com.weatherapplication.core.base.getValue
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable

fun getUnitSymbol(type: String) =
    when (type) {
        TEMPERATURE -> "°C"
        WINDSPEED -> " m/s"
        HUMIDITY -> " %"
        PRECIPITATION -> " mm"
        UVINDEX -> " uv"
        VISIBILITY -> " km"
        else -> ""
    }

const val TEMPERATURE = "temperature"
const val WINDSPEED = "windSpeed"
const val HUMIDITY = "humidity"
const val PRECIPITATION = "precipitation"
const val UVINDEX = "uvIndex"
const val VISIBILITY = "visibility"

fun getListWidget(weatherDisplayable: WeatherDisplayable): List<Triple<String, String, Int>> {
    val windSpeed = getValue(weatherDisplayable.windSpeed, WINDSPEED)
    val windSpeedT = Triple(windSpeed, "Wind", R.drawable.weather_windy)
    val humidity = getValue(weatherDisplayable.humidity, HUMIDITY)
    val humidityT = Triple(humidity, "Humidity", R.drawable.water_percent)
    val precipitation = getValue(weatherDisplayable.precipitation, PRECIPITATION)
    val precipitationT = Triple(precipitation, "Precipitation", R.drawable.weather_pouring)
    val uvIndex = getValue(weatherDisplayable.uvIndex, UVINDEX)
    val uvIndexT = Triple(uvIndex, "Index UV", R.drawable.sun_wireless)
    val feelLike = getValue(weatherDisplayable.feelLike, TEMPERATURE)
    val feelLikeT = Triple(feelLike, "Feellike", R.drawable.sun_thermometer)
    val visibility = getValue(weatherDisplayable.feelLike, VISIBILITY)
    val visibilityT = Triple(visibility, "Visibility", R.drawable.eye)
    return listOf(windSpeedT, humidityT, precipitationT, uvIndexT, feelLikeT, visibilityT)
}
