package com.weatherapplication.feature.cityweather.domain.usecase

import com.weatherapplication.core.base.UseCase
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import javax.inject.Inject

class GetCityFromDatabaseUseCase @Inject constructor(private val weatherCityRepository: WeatherCityRepository) : UseCase<SearchCity, String>() {
    override suspend fun action(params: String) = weatherCityRepository.getCity(params)
}
