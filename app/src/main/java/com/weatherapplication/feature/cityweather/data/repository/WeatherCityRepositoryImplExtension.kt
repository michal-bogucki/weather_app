package com.weatherapplication.feature.cityweather.data.repository

import com.weatherapplication.feature.cityweather.data.remote.Forecast
import com.weatherapplication.feature.cityweather.data.remote.ForecastDay
import com.weatherapplication.feature.cityweather.data.remote.Hour
import com.weatherapplication.feature.cityweather.data.remote.WeatherModelRemote
import com.weatherapplication.feature.cityweather.domain.model.HourTemperature
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import java.time.LocalDate
import java.time.LocalDateTime

fun createWeatherCached(
    city: SearchCity,
    forecast: Forecast?,
    index: Int,
    weatherNext: ForecastDay,
): WeatherData {
    val weather = WeatherData(
        cityName = city.cityName,
        date = LocalDate.parse(forecast?.forecastday?.get(index)?.date ?: LocalDate.now().toString()),
        lastUpdate = LocalDateTime.now(),
        countryName = city.countryName,
        lat = city.lat,
        lon = city.lon,
        weatherIcon = weatherNext.day?.condition?.icon,
        temperature = forecast?.forecastday?.get(index)?.day?.avgtempC,
        minTemperature = forecast?.forecastday?.get(index)?.day?.mintempC,
        maxTemperature = forecast?.forecastday?.get(index)?.day?.maxtempC,
        conditionWeatherName = weatherNext.day?.condition?.text,
        sunrise = forecast?.forecastday?.get(index)?.astro?.sunrise,
        sunset = forecast?.forecastday?.get(index)?.astro?.sunset,
        windSpeed = weatherNext.day?.maxwindKph,
        humidity = weatherNext.day?.avghumidity,
        precipitation = weatherNext.day?.totalprecipMm,
        uvIndex = weatherNext.day?.uv,
        feelLike = null,
        visibility = null,
        listHourTemperature = forecast?.forecastday?.get(index)?.hour?.map { hour ->
            HourTemperature(
                getHour(hour),
                hour.temp_c ?: Double.MAX_VALUE,
                hour.condition?.icon,
            )
        } ?: listOf(),
    )
    return weather
}

fun createWeatherCached(
    city: SearchCity,
    weatherRemote: WeatherModelRemote,
): WeatherData {
    val todayWeather = WeatherData(
        cityName = city.cityName,
        date = LocalDate.now(),
        lastUpdate = LocalDateTime.now(),
        countryName = city.countryName,
        lat = city.lat,
        lon = city.lon,
        weatherIcon = weatherRemote.current?.condition?.icon,
        temperature = weatherRemote.current?.tempC,
        minTemperature = weatherRemote.forecast?.forecastday?.get(0)?.day?.mintempC,
        maxTemperature = weatherRemote.forecast?.forecastday?.get(0)?.day?.maxtempC,
        conditionWeatherName = weatherRemote.current?.condition?.text,
        sunrise = weatherRemote.forecast?.forecastday?.get(0)?.astro?.sunrise,
        sunset = weatherRemote.forecast?.forecastday?.get(0)?.astro?.sunset,
        windSpeed = weatherRemote.current?.windKph,
        humidity = weatherRemote.current?.humidity,
        precipitation = weatherRemote.current?.precipMm,
        uvIndex = weatherRemote.current?.uv,
        feelLike = weatherRemote.current?.feelslikeC,
        visibility = weatherRemote.current?.visKm,
        listHourTemperature = weatherRemote.forecast?.forecastday?.get(0)?.hour?.map { hour ->
            HourTemperature(
                getHour(hour),
                hour.temp_c ?: Double.MAX_VALUE,
                hour.condition?.icon,
            )
        } ?: listOf(),
    )
    return todayWeather
}

fun getHour(hour: Hour): String {
    val hour = hour.time?.split(" ")
    return when {
        hour.isNullOrEmpty() -> {
            ""
        }
        hour.size < 2 -> {
            ""
        }
        else -> {
            hour[1]
        }
    }
}
