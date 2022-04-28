package com.weatherapplication.feature.cityweather.domain.usecase

import com.weatherapplication.core.base.Resource
import com.weatherapplication.core.base.UseCase
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityWeatherUseCase @Inject constructor(
    private val getCityFromDatabaseUseCase: GetCityFromDatabaseUseCase,
    private val weatherCityRepository: WeatherCityRepository
) : UseCase<Flow<Resource<List<WeatherData>>>, String>() {
    override suspend fun action(params: String): Flow<Resource<List<WeatherData>>> {
        val city = getCityFromDatabaseUseCase.action(params)
        return weatherCityRepository.getWeather(city)
    }
}
