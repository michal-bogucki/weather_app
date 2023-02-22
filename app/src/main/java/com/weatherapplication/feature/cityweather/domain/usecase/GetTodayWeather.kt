package com.weatherapplication.feature.cityweather.domain.usecase



import com.weatherapplication.core.base.Resource
import com.weatherapplication.core.base.SubjectUseCase
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodayWeather @Inject constructor(private val weatherCityRepository: WeatherCityRepository) :
    SubjectUseCase<GetTodayWeather.Params, Resource<WeatherData>>() {

    data class Params(val cityId: String)

    override fun createObservable(params: Params): Flow<Resource<WeatherData>> {
        val city = weatherCityRepository.getCity(params.cityId)
        return weatherCityRepository.getWeather(city)
    }
}
