package com.weatherapplication.feature.cityweather.domain.usecase

import android.content.Context
import android.location.Geocoder
import com.weatherapplication.core.base.Resource
import com.weatherapplication.core.base.SubjectUseCase
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.repository.LocationRepository
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class GetNextDayWeatherUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val weatherCityRepository: WeatherCityRepository,
    private val locationRepository: LocationRepository,
) :
    SubjectUseCase<GetNextDayWeatherUseCase.Params, Resource<List<WeatherData>>>() {

    data class Params(val cityId: String?)

    override fun createObservable(params: Params): Flow<Resource<List<WeatherData>>> {
        return if (params.cityId.isNullOrEmpty()) {
            flow {
                val currentLocation = locationRepository.getCurrentLocation()
                val geoCoder = Geocoder(context, Locale.getDefault())
                currentLocation?.let {
                    val addresses = geoCoder.getFromLocation(
                        it.latitude,
                        it.longitude,
                        1,
                    )
                    if (addresses != null && addresses.isNotEmpty()) {
                        val cityName: String = addresses[0].locality
                        val country: String = addresses[0].countryName

                        weatherCityRepository.saveChooseCitySearch(SearchCity(cityName, country, it.latitude, it.longitude, false))
                        val city = weatherCityRepository.getCity(cityName)
                        emitAll(weatherCityRepository.getWeatherNextDays(city))
                    }
                }
                emit(Resource.error(""))
            }
        } else flow {
            val city = weatherCityRepository.getCity(params.cityId)
            emitAll(weatherCityRepository.getWeatherNextDays(city))
        }
    }
}
