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
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.abs

class WeatherCityRepositoryImpl @Inject constructor(
    private val weatherLocalDataSource: WeatherLocalDataSource,
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
) : WeatherCityRepository {
    override suspend fun getCity(city: String) = weatherLocalDataSource.getCity(city)

    override fun getWeatherToday(city: SearchCity): Flow<Resource<WeatherData>> {
        val networkBoundFlow = networkLocalBoundResource(
            fetchFromLocal = {
                getWeatherFromLocal(city, LocalDate.now())
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

    override fun getWeatherNextDays(city: SearchCity): Flow<Resource<List<WeatherData>>> {
        val networkBoundFlow = networkLocalBoundResource(
            fetchFromLocal = {
                getWeatherFromLocalNextDays(city, listOf(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2)))
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
                weatherCachedList.map { it.toWeatherData() }
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

    private fun refreshData(weather: List<WeatherCached?>?): Boolean {
        if (weather.isNullOrEmpty()) return true
        return false
    }

    private suspend fun getWeatherFromApi(city: SearchCity) = weatherRemoteDataSource.getWeatherFromApi(city)

    private fun getWeatherFromLocal(city: SearchCity, now: LocalDate) = weatherLocalDataSource.getWeatherFromLocal(city, now)
    private fun getWeatherFromLocalNextDays(city: SearchCity, dateList: List<LocalDate>) =
        weatherLocalDataSource.getWeatherFromLocalNextDays(city, dateList)

    private fun saveWeatherToDatabase(weatherRemote: WeatherModelRemote, city: SearchCity) =
        weatherLocalDataSource.saveWeatherToDatabase(weatherRemote, city)
}
