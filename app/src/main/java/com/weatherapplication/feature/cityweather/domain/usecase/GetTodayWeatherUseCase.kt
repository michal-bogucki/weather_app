package com.weatherapplication.feature.cityweather.domain.usecase

import com.weatherapplication.core.base.Resource
import com.weatherapplication.core.base.SubjectUseCase
import com.weatherapplication.core.network.NetworkStateProvider
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTodayWeatherUseCase @Inject constructor(
    private val weatherCityRepository: WeatherCityRepository,
    private val networkStateProvider: NetworkStateProvider,
) :
    SubjectUseCase<GetTodayWeatherUseCase.Params, Resource<WeatherData>>() {

    data class Params(val cityId: String)

    override fun createObservable(params: Params): Flow<Resource<WeatherData>> {
        return flow {
            if (networkStateProvider.isNetworkAvailable()) {
                val city = weatherCityRepository.getCity(params.cityId)
                emitAll(weatherCityRepository.getWeatherToday(city))
            } else {
                emit(Resource.error("Error 404"))
            }
        }
    }
}
