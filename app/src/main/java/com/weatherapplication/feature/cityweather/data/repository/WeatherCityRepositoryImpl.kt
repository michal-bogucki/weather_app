package com.weatherapplication.feature.cityweather.data.repository


import com.weatherapplication.core.base.Resource
import com.weatherapplication.core.base.networkLocalBoundResource
import com.weatherapplication.feature.cityweather.data.api.WeatherApiService
import com.weatherapplication.feature.cityweather.data.api.WeatherModelRemote
import com.weatherapplication.feature.cityweather.data.local.dao.WeatherDao
import com.weatherapplication.feature.cityweather.domain.model.DayWeather
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import com.weatherapplication.feature.searchcity.data.local.dao.SearchCityDao
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherCityRepositoryImpl @Inject constructor(
    private val searchCityDao: SearchCityDao,
    private val weatherApiService: WeatherApiService,
    private val weatherDao: WeatherDao
) :
    WeatherCityRepository {
    override suspend fun getCity(city: String) =
        searchCityDao.getSearchCityById(city).toSearchCity()

    override fun getWeather(city: SearchCity): Flow<Resource<List<WeatherData>>> {
        val networkBoundFlow = networkLocalBoundResource(
            fetchFromLocal = {
                weatherDao.getWeatherById(city.cityName)
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
                weatherApiService.getWeather(query = "${city.lat},${city.lon}")
            },
            saveFetchResult = {
                saveWeatherToDatabase(it)
            },
        )
        return networkBoundFlow.flowOn(Dispatchers.IO)
    }

    private fun saveWeatherToDatabase(weatherRemote: WeatherModelRemote) {
        TODO("Not yet implemented")
    }


}
