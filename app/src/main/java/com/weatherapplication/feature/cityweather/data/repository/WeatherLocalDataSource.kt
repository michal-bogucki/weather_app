package com.weatherapplication.feature.cityweather.data.repository

import com.weatherapplication.feature.cityweather.data.local.dao.WeatherDao
import com.weatherapplication.feature.cityweather.data.local.model.createWeatherCached
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.searchcity.data.local.dao.SearchCityDao
import com.weatherapplication.feature.searchcity.data.local.model.SearchCityCached
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import java.time.LocalDate
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(
    private val searchCityDao: SearchCityDao,
    private val weatherDao: WeatherDao
) {

    suspend fun saveChooseCitySearch(searchCity: SearchCity) = searchCityDao.saveSearchCity(
        SearchCityCached(searchCity)
    )
    suspend fun getCity(city: String) = searchCityDao.getSearchCityById(city).toSearchCity()

    fun getWeatherFromLocal(city: SearchCity, now: LocalDate) = weatherDao.getWeatherById(
        city.cityName,
        now
    )
    fun getWeatherFromLocalNextDays(city: SearchCity, dateList: LocalDate) = weatherDao.getWeatherNextDay(
        city.cityName,
        dateList
    )

    fun saveWeatherToDatabase(weather: List<WeatherData>) {
        weather.map { createWeatherCached(it) }.toTypedArray().let {
            weatherDao.saveWeather(*it)
        }
    }
}
