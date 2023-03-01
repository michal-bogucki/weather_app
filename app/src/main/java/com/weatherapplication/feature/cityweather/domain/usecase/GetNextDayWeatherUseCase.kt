package com.weatherapplication.feature.cityweather.domain.usecase

import com.weatherapplication.core.base.Resource
import com.weatherapplication.core.base.SubjectUseCase
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNextDayWeatherUseCase @Inject constructor(private val weatherCityRepository: WeatherCityRepository) :
    SubjectUseCase<GetNextDayWeatherUseCase.Params, Resource<List<WeatherData>>>() {

    data class Params(val cityId: String)

    override fun createObservable(params: Params): Flow<Resource<List<WeatherData>>> {
        return flow {
            val city = weatherCityRepository.getCity(params.cityId)

            emitAll(weatherCityRepository.getWeatherNextDays(city))
        }
    }
}
