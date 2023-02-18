package com.weatherapplication.feature.cityweather.data.repository

import com.weatherapplication.feature.cityweather.data.api.Forecast
import com.weatherapplication.feature.cityweather.data.api.ForecastDay
import com.weatherapplication.feature.cityweather.data.api.Hour
import com.weatherapplication.feature.cityweather.data.api.WeatherModelRemote
import com.weatherapplication.feature.cityweather.data.local.model.HourTemperatureCached
import com.weatherapplication.feature.cityweather.data.local.model.WeatherCached
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import java.time.LocalDate
import java.time.LocalDateTime

fun createWeatherCached(
    city: SearchCity,
    forecast: Forecast,
    index: Int,
    weatherNext: ForecastDay
): WeatherCached {//sealed class (value, emptyState)
    val weather = WeatherCached(
        cityName = city.cityName,
        date = LocalDate.parse(forecast.forecastday[index].date),
        lastUpdate = LocalDateTime.now(),
        countryName = city.countryName,
        lat = city.lat,
        lon = city.lon,
        weatherIcon = weatherNext.day?.condition?.icon ?: "",
        temperature = Double.MAX_VALUE,
        minTemperature = forecast.forecastday[index].day?.mintempC
            ?: Double.MAX_VALUE,
        maxTemperature = forecast.forecastday[index].day?.maxtempC
            ?: Double.MAX_VALUE,
        conditionWeatherName = weatherNext.day?.condition?.text ?: "",
        sunrise = forecast.forecastday[index].astro.sunrise ?: "",
        sunset = forecast.forecastday[index].astro.sunset ?: "",
        windSpeed = weatherNext.day?.maxwindKph ?: Double.MAX_VALUE,
        humidity = weatherNext.day?.avghumidity ?: Int.MAX_VALUE,
        precipitation = weatherNext.day?.totalprecipMm ?: Double.MAX_VALUE,
        uvIndex = weatherNext.day?.uv ?: Double.MAX_VALUE,
        feelLike = Double.MAX_VALUE,
        visibility = Double.MAX_VALUE,
        listHourTemperature = forecast.forecastday[index].hour?.map { hour ->
            HourTemperatureCached(
                getHour(hour),
                hour.temp_c ?: Double.MAX_VALUE,
                hour.condition?.icon ?: ""
            )
        } ?: listOf()
    )
    return weather
}

fun createWeatherCached(
    city: SearchCity,
    weatherRemote: WeatherModelRemote
): WeatherCached {
    val todayWeather = WeatherCached(
        cityName = city.cityName,
        date = LocalDate.now(),
        lastUpdate = LocalDateTime.now(),
        countryName = city.countryName,
        lat = city.lat,
        lon = city.lon,
        weatherIcon = weatherRemote.current?.condition?.icon ?: "",
        temperature = weatherRemote.current?.tempC ?: Double.MAX_VALUE,
        minTemperature = weatherRemote.forecast?.forecastday?.get(0)?.day?.mintempC
            ?: Double.MAX_VALUE,
        maxTemperature = weatherRemote.forecast?.forecastday?.get(0)?.day?.maxtempC
            ?: Double.MAX_VALUE,
        conditionWeatherName = weatherRemote.current?.condition?.text ?: "",
        sunrise = weatherRemote.forecast?.forecastday?.get(0)?.astro?.sunrise ?: "",
        sunset = weatherRemote.forecast?.forecastday?.get(0)?.astro?.sunset ?: "",
        windSpeed = weatherRemote.current?.windKph ?: Double.MAX_VALUE,
        humidity = weatherRemote.current?.humidity ?: Int.MAX_VALUE,
        precipitation = weatherRemote.current?.precipMm ?: Double.MAX_VALUE,
        uvIndex = weatherRemote.current?.uv ?: Double.MAX_VALUE,
        feelLike = weatherRemote.current?.feelslikeC ?: Double.MAX_VALUE,
        visibility = weatherRemote.current?.visKm ?: Double.MAX_VALUE,
        listHourTemperature = weatherRemote.forecast?.forecastday?.get(0)?.hour?.map { hour ->
            HourTemperatureCached(
                getHour(hour), hour.temp_c ?: Double.MAX_VALUE, hour.condition?.icon ?: ""
            )
        } ?: listOf()
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
