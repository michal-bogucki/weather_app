package com.weatherapplication.feature.cityweather.data.repository

import com.weatherapplication.feature.cityweather.data.local.dao.WeatherDao
import com.weatherapplication.feature.cityweather.data.local.model.WeatherCached
import com.weatherapplication.feature.cityweather.data.remote.WeatherModelRemote
import com.weatherapplication.feature.searchcity.data.local.dao.SearchCityDao
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(
    private val searchCityDao: SearchCityDao,
    private val weatherDao: WeatherDao,
) {
    fun getCity(city: String) = searchCityDao.getSearchCityById(city).toSearchCity()

    fun getWeatherFromLocal(city: SearchCity) = weatherDao.getWeatherById(city.cityName)

    fun saveWeatherToDatabase(weatherRemote: WeatherModelRemote, city: SearchCity) {
        val list: MutableList<WeatherCached> = mutableListOf()
        val todayWeather = createWeatherCached(city, weatherRemote)
        list.add(todayWeather)
        val forecast = weatherRemote.forecast?.forecastday
        if (forecast != null) {
            for ((index, weatherNext) in forecast.withIndex()) {
                if (index != 0) {
                    val weather = createWeatherCached(city, weatherRemote.forecast, index, weatherNext)
                    list.add(weather)
                }
            }
        }
        list.toTypedArray().let {
            weatherDao.saveWeather(*it)
        }
    }
}
