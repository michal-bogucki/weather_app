package com.weatherapplication.data.models.weather.transformer

import com.weatherapplication.data.models.weather.api.Forecast
import com.weatherapplication.data.models.weather.api.Forecastday
import com.weatherapplication.data.models.weather.api.WeatherModelRemote
import com.weatherapplication.data.models.weather.database.HourTemp
import com.weatherapplication.data.models.weather.database.NextDay
import com.weatherapplication.data.models.weather.database.WeatherModelLocal
import java.time.LocalDateTime

object WeatherTransformer {


    fun transform(weatherModelRemote: WeatherModelRemote): WeatherModelLocal =
        with(weatherModelRemote) {
            WeatherModelLocal(
                id = 0,
                city = weatherModelRemote.location?.name ?: "",
                country = weatherModelRemote.location?.country ?: "",
                lat = weatherModelRemote.location?.lat ?: Double.MIN_VALUE,
                lon = weatherModelRemote.location?.lon ?: Double.MIN_VALUE,
                temp = weatherModelRemote.current?.temp_c ?: Double.MIN_VALUE,
                date = LocalDateTime.now(),
                tempFeelsLike = weatherModelRemote.current?.feelslike_c ?: Double.MIN_VALUE,
                conditionText = weatherModelRemote.current?.condition?.text ?: "",
                conditionIcon = weatherModelRemote.current?.condition?.icon ?: "",
                isDay = weatherModelRemote.current?.is_day == 1,
                humidity = weatherModelRemote.current?.humidity ?: Int.MIN_VALUE,
                cloud = weatherModelRemote.current?.cloud ?: Int.MIN_VALUE,
                wind = weatherModelRemote.current?.wind_kph ?: Double.MIN_VALUE,
                pressure = weatherModelRemote.current?.pressure_mb ?: Double.MIN_VALUE,
                temperatureList = getTempListFromApi(weatherModelRemote.forecast),
                nextDayWeather = getNextDayWeatherFromApi(weatherModelRemote.forecast)
            )
        }

    private fun getNextDayWeatherFromApi(forecast: Forecast?): List<NextDay> {
        var list = mutableListOf<NextDay>()
        forecast?.let {
            it.forecastday.map { forecastday ->
                NextDay(
                    forecastday.date,
                    forecastday.day.maxtemp_c,
                    forecastday.day.mintemp_c,
                    forecastday.day.condition.text,
                    forecastday.day.condition.icon,
                    getTempListFromApi(forecastday)
                )
            }
        }
        return list
    }

    private fun getTempListFromApi(forecast: Forecast?): List<HourTemp> {
        var list = mutableListOf<HourTemp>()
        forecast?.let {
            val forecastDay = it.forecastday[0]
            list.addAll(forecastDay.hour.map { hour ->
                HourTemp(hour.time, hour.temp_c, hour.condition.text, hour.condition.icon)
            })
        }
        return list
    }

    private fun getTempListFromApi(forecast: Forecastday?): List<HourTemp> {
        var list = mutableListOf<HourTemp>()
        forecast?.let { forecastDay ->
            list.addAll(forecastDay.hour.map { hour ->
                HourTemp(hour.time, hour.temp_c, hour.condition.text, hour.condition.icon)
            })
        }
        return list
    }

}