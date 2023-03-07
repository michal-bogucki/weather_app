package com.weatherapplication.feature.cityweather.domain.repository

import com.weatherapplication.core.base.Resource
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import kotlinx.coroutines.flow.Flow

interface WeatherCityRepository {
    suspend fun getCity(city: String): SearchCity

    suspend fun getLastCity(): SearchCity?
    fun getWeatherToday(city: SearchCity): Flow<Resource<WeatherData>>

    fun getWeatherNextDays(city: SearchCity): Flow<Resource<List<WeatherData>>>
}
