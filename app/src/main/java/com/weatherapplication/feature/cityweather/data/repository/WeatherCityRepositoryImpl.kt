package com.weatherapplication.feature.cityweather.data.repository

import com.weatherapplication.core.base.Resource
import com.weatherapplication.core.base.networkLocalBoundResource
import com.weatherapplication.feature.cityweather.data.api.WeatherApiService
import com.weatherapplication.feature.cityweather.data.api.WeatherModelRemote
import com.weatherapplication.feature.cityweather.data.local.dao.WeatherDao
import com.weatherapplication.feature.cityweather.data.local.model.WeatherCached
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import com.weatherapplication.feature.searchcity.data.local.dao.SearchCityDao
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.abs

class WeatherCityRepositoryImpl @Inject constructor(
    private val searchCityDao: SearchCityDao,
    private val weatherApiService: WeatherApiService,
    private val weatherDao: WeatherDao
) : WeatherCityRepository {
    override suspend fun getCity(city: String) = searchCityDao.getSearchCityById(city).toSearchCity()

    override fun getWeather(city: SearchCity): Flow<Resource<List<WeatherData>>> {
        val networkBoundFlow = networkLocalBoundResource(
            fetchFromLocal = {
                getWeatherFromLocal(city)
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
                getWeatherFromApi(city)
            },
            saveFetchResult = {
                saveWeatherToDatabase(it, city)
            },
        )
        return networkBoundFlow.flowOn(Dispatchers.IO)
    }

    private fun refreshData(list: List<WeatherData>?): Boolean {
        if (list.isNullOrEmpty()) return true
        if (list[0].date != LocalDate.now()) return true
        if (abs(list[0].lastUpdate.hour - LocalDateTime.now().hour) > 2) return true
        return false
    }

    private suspend fun getWeatherFromApi(city: SearchCity) = weatherApiService.getWeather(query = "${city.lat},${city.lon}")

    private fun getWeatherFromLocal(city: SearchCity) = weatherDao.getWeatherById(city.cityName).map { weather -> weather.map { it.toWeatherData() } }

    private fun saveWeatherToDatabase(weatherRemote: WeatherModelRemote, city: SearchCity) {
        val list: MutableList<WeatherCached> = mutableListOf()
        val todayWeather = createWeatherCached(city, weatherRemote)
        list.add(todayWeather)
        val forecast = weatherRemote.forecast?.forecastday
        if (forecast != null)
            for ((index, weatherNext) in forecast.withIndex()) {
                if (index != 0) {
                    val weather = createWeatherCached(city, weatherRemote.forecast, index, weatherNext)
                    list.add(weather)
                }
            }
        list.toTypedArray().let {
            weatherDao.saveWeather(*it)
        }
    }
}
