package com.weatherapplication.feature.cityweather.data.repository

import com.weatherapplication.core.base.Resource
import com.weatherapplication.core.base.networkLocalBoundResource
import com.weatherapplication.feature.cityweather.data.local.model.WeatherCached
import com.weatherapplication.feature.cityweather.data.remote.WeatherModelRemote
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.abs

class WeatherCityRepositoryImpl @Inject constructor(
    private val weatherLocalDataSource: WeatherLocalDataSource,
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
) : WeatherCityRepository {
    override fun getCity(city: String) = weatherLocalDataSource.getCity(city)

    override fun getWeather(city: SearchCity): Flow<Resource<WeatherData>> {
        val networkBoundFlow = networkLocalBoundResource(
            fetchFromLocal = {
                getWeatherFromLocal(city).mapNotNull { list ->
                    if (list.isNotEmpty()) {
                        list.first {
                            it.date == LocalDate.now()
                        }
                    } else {
                        null
                    }
                }
            },
            shouldFetchFromRemote = { weatherCachedList ->
                refreshData(weatherCachedList)
            },
            shouldFetchFromLocale = {
                false
            },
            fetchFromRemote = {
                getWeatherFromApi(city)
            },
            saveFetchResult = { weatherModelRemote ->
                saveWeatherToDatabase(weatherModelRemote, city)
            },
            changeToDomain = { weatherCachedList ->
                weatherCachedList.toWeatherData()
            },
        )
        return networkBoundFlow.flowOn(Dispatchers.IO)
    }

    private fun refreshData(weather: WeatherCached?): Boolean {
        if (weather == null) return true
        if (weather.date != LocalDate.now()) return true
        if (abs(weather.lastUpdate.hour - LocalDateTime.now().hour) > 2) return true
        return false
    }

    private suspend fun getWeatherFromApi(city: SearchCity) = weatherRemoteDataSource.getWeatherFromApi(city)

    private fun getWeatherFromLocal(city: SearchCity) = weatherLocalDataSource.getWeatherFromLocal(city)

    private fun saveWeatherToDatabase(weatherRemote: WeatherModelRemote, city: SearchCity) =
        weatherLocalDataSource.saveWeatherToDatabase(weatherRemote, city)
}
