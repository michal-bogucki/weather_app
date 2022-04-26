package com.weatherapplication.feature.cityweather.domain.repository

import com.weatherapplication.core.base.Resource
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import kotlinx.coroutines.flow.Flow

interface WeatherCityRepository {
    suspend fun getCity(params: String): SearchCity
    fun getWeather(city: SearchCity): Flow<Resource<List<WeatherData>>>
}
