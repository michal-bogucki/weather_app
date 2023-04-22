package com.weatherapplication.feature.cityweather.domain.usecase

import android.content.Context
import com.weatherapplication.core.base.Resource
import com.weatherapplication.core.base.SubjectUseCase
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.repository.LocationRepository
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetTodayWeatherUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val weatherCityRepository: WeatherCityRepository,
    private val locationRepository: LocationRepository
) : SubjectUseCase<GetTodayWeatherUseCase.Params, Resource<WeatherData>>() {

    data class Params(val cityId: String?)

    override fun createObservable(params: Params): Flow<Resource<WeatherData>> {
        return if (params.cityId.isNullOrEmpty()) {
            flow {
                val addresses = locationRepository.getAddress()
                if (addresses.isNullOrEmpty()) {
                    emit(Resource.error("Not GPS"))
                } else {
                    val cityName = addresses[0].locality
                    val country = addresses[0].countryName
                    val latitude = addresses[0].latitude
                    val longitude = addresses[0].longitude

                    weatherCityRepository.saveChooseCitySearch(
                        SearchCity(cityName, country, latitude, longitude, false)
                    )
                    val city = weatherCityRepository.getCity(cityName)
                    emitAll(weatherCityRepository.getWeatherToday(city))
                }
            }
        } else {
            flow {
                val city = weatherCityRepository.getCity(params.cityId)
                emitAll(weatherCityRepository.getWeatherToday(city))
            }
        }
    }
}
