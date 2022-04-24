package com.weatherapplication.feature.cityweather.domain.usecase

import com.bumptech.glide.load.engine.Resource
import com.weatherapplication.core.base.UseCase
import com.weatherapplication.feature.cityweather.domain.model.DayWeather
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCityWeatherUseCase @Inject constructor(
    private val getCityFromDatabaseUseCase: GetCityFromDatabaseUseCase,
    private val weatherCityRepository: WeatherCityRepository
) : UseCase<Flow<Resource<List<DayWeather>>>, String>() {
    override suspend fun action(params: String): Flow<Resource<List<DayWeather>>> {
        val city = getCityFromDatabaseUseCase.action(params)
        return weatherCityRepository.getWeather(city)
    }

}