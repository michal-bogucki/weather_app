package com.weatherapplication.feature.cityweather.data.repository

import com.weatherapplication.core.base.Resource
import com.weatherapplication.core.base.networkLocalBoundResource
import com.weatherapplication.feature.cityweather.data.api.WeatherModelRemote
import com.weatherapplication.feature.cityweather.data.local.model.WeatherCached
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.abs

class WeatherCityRepositoryImpl @Inject constructor(
    private val weatherLocalDataSource: WeatherLocalDataSource,
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherCityRepository {
    override suspend fun getCity(city: String) = weatherLocalDataSource.getCity(city)

    override fun getWeather(city: SearchCity): Flow<Resource<List<WeatherData>>> {
        val networkBoundFlow = networkLocalBoundResource(
            fetchFromLocal = {
                getWeatherFromLocal(city)
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
                weatherCachedList.map { weatherCached ->
                    weatherCached.toWeatherData()
                }
            }
        )
        return networkBoundFlow.flowOn(Dispatchers.IO)
    }

    private fun refreshData(list: List<WeatherCached>?): Boolean {
        if (list.isNullOrEmpty()) return true
        if (list[0].date != LocalDate.now()) return true
        if (abs(list[0].lastUpdate.hour - LocalDateTime.now().hour) > 2) return true
        return false
    }

    private suspend fun getWeatherFromApi(city: SearchCity) = weatherRemoteDataSource.getWeatherFromApi(city)

    private fun getWeatherFromLocal(city: SearchCity) = weatherLocalDataSource.getWeatherFromLocal(city)

    private fun saveWeatherToDatabase(weatherRemote: WeatherModelRemote, city: SearchCity) =
        weatherLocalDataSource.saveWeatherToDatabase(weatherRemote, city)
}
