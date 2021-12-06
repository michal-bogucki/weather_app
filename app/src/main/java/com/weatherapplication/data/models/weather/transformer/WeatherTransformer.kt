package com.weatherapplication.data.models.weather.transformer

import com.weatherapplication.data.models.weather.api.Forecast
import com.weatherapplication.data.models.weather.api.Forecastday
import com.weatherapplication.data.models.weather.api.WeatherModelRemote
import com.weatherapplication.data.models.weather.database.HourTemp
import com.weatherapplication.data.models.weather.database.NextDay
import com.weatherapplication.data.models.weather.database.WeatherModelLocal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object WeatherTransformer {


    fun transform(
        weatherModelRemote: WeatherModelRemote,
        weatherId: Int,
        name: String
    ): WeatherModelLocal =
        with(weatherModelRemote) {
            WeatherModelLocal(
                id = weatherId,
                city = name,
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
        var id = 0
        forecast?.let {
            it.forecastday.map { forecastday ->
                NextDay(
                    forecastday.date ?: "",
                    forecastday.day?.maxtemp_c ?: Double.MIN_VALUE,
                    forecastday.day?.mintemp_c ?: Double.MIN_VALUE,
                    forecastday.day?.condition?.text ?: "",
                    forecastday.day?.condition?.icon ?: "",
                    id++
                )
            }.let { mapList -> list.addAll(mapList) }
        }
        return list
    }

    private fun getTempListFromApi(forecast: Forecast?): List<HourTemp> {
        var list = mutableListOf<HourTemp>()
        var id = 0
        forecast?.let {
            val forecastDay = it.forecastday[0]
            forecastDay.hour?.map { hour ->
                val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val localDateTime = LocalDateTime.parse(hour.time,firstApiFormat)
                val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                HourTemp(
                    formatter.format(localDateTime),
                    hour.temp_c ?: Double.MIN_VALUE,
                    hour.condition?.text ?: "",
                    hour.condition?.icon ?: "",
                    id++
                )
            }?.let { mapList -> list.addAll(mapList) }
        }
        return list
    }

}