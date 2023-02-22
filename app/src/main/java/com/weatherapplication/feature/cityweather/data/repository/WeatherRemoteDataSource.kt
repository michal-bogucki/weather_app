package com.weatherapplication.feature.cityweather.data.repository

import com.weatherapplication.feature.cityweather.data.remote.WeatherApiService
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherApiService: WeatherApiService
){
    suspend fun getWeatherFromApi(city: SearchCity) = weatherApiService.getWeather(query = "${city.lat},${city.lon}")
}
