package com.weatherapplication.feature.cityweather.data.repository

import com.weatherapplication.core.base.Resource
import com.weatherapplication.core.base.networkLocalBoundResource
import com.weatherapplication.feature.cityweather.data.api.Hour
import com.weatherapplication.feature.cityweather.data.api.WeatherApiService
import com.weatherapplication.feature.cityweather.data.api.WeatherModelRemote
import com.weatherapplication.feature.cityweather.data.local.dao.WeatherDao
import com.weatherapplication.feature.cityweather.data.local.model.HourTemperatureCached
import com.weatherapplication.feature.cityweather.data.local.model.WeatherCached
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import com.weatherapplication.feature.searchcity.data.local.dao.SearchCityDao
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
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
                getWeatherFromLocal(city)
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
                getWeatherFromApi(city)
            },
            saveFetchResult = {
                saveWeatherToDatabase(it, city)
            },
        )
        return networkBoundFlow.flowOn(Dispatchers.IO)
    }

    private suspend fun getWeatherFromApi(city: SearchCity) =
        weatherApiService.getWeather(query = "${city.lat},${city.lon}")

    private fun getWeatherFromLocal(city: SearchCity) =
        weatherDao.getWeatherById(city.cityName)
            .map { weather -> weather.map { it.toWeatherData() } }

    private fun saveWeatherToDatabase(weatherRemote: WeatherModelRemote, city: SearchCity) {
        val list: MutableList<WeatherCached> = mutableListOf()
        val todayWeather = WeatherCached(
            cityName = city.cityName,
            date = LocalDate.now(),
            lastUpdate = LocalDateTime.now(),
            countryName = city.countryName,
            lat = city.lat,
            lon = city.lon,
            weatherIcon = weatherRemote.current?.condition?.icon ?: "",
            temperature = weatherRemote.current?.tempC ?: Double.MAX_VALUE,
            minTemperature = weatherRemote.forecast?.forecastday?.get(0)?.day?.mintempC
                ?: Double.MAX_VALUE,
            maxTemperature = weatherRemote.forecast?.forecastday?.get(0)?.day?.maxtempC
                ?: Double.MAX_VALUE,
            conditionWeatherName = weatherRemote.current?.condition?.text ?: "",
            sunrise = weatherRemote.forecast?.forecastday?.get(0)?.astro?.sunrise ?: "",
            sunset = weatherRemote.forecast?.forecastday?.get(0)?.astro?.sunset ?: "",
            windSpeed = weatherRemote.current?.windKph ?: Double.MAX_VALUE,
            humidity = weatherRemote.current?.humidity ?: Int.MAX_VALUE,
            precipitation = weatherRemote.current?.precipMm ?: Double.MAX_VALUE,
            uvIndex = weatherRemote.current?.uv ?: Double.MAX_VALUE,
            feelLike = weatherRemote.current?.feelslikeC ?: Double.MAX_VALUE,
            visibility = weatherRemote.current?.visKm ?: Double.MAX_VALUE,
            listHourTemperature = weatherRemote.forecast?.forecastday?.get(0)?.hour?.map { hour ->
                HourTemperatureCached(
                    getHour(hour), hour.temp_c ?: Double.MAX_VALUE, hour.condition?.icon ?: ""
                )
            } ?: listOf()
        )
        list.add(todayWeather)
        val forecast = weatherRemote.forecast?.forecastday
        if (forecast != null)
            for ((index, weatherNext) in forecast.withIndex()) {
                if (index != 0) {
                    val weather = WeatherCached(
                        cityName = city.cityName,
                        date = LocalDate.parse(weatherRemote.forecast.forecastday[index].date),
                        lastUpdate = LocalDateTime.now(),
                        countryName = city.countryName,
                        lat = city.lat,
                        lon = city.lon,
                        weatherIcon = weatherNext.day?.condition?.icon ?: "",
                        temperature = Double.MAX_VALUE,
                        minTemperature = weatherRemote.forecast.forecastday[index].day?.mintempC
                            ?: Double.MAX_VALUE,
                        maxTemperature = weatherRemote.forecast.forecastday[index].day?.maxtempC
                            ?: Double.MAX_VALUE,
                        conditionWeatherName = weatherNext.day?.condition?.text ?: "",
                        sunrise = weatherRemote.forecast.forecastday[index].astro.sunrise ?: "",
                        sunset = weatherRemote.forecast.forecastday[index].astro.sunset ?: "",
                        windSpeed = weatherNext.day?.maxwindKph ?: Double.MAX_VALUE,
                        humidity = weatherNext.day?.avghumidity ?: Int.MAX_VALUE,
                        precipitation = weatherNext.day?.totalprecipMm ?: Double.MAX_VALUE,
                        uvIndex = weatherNext.day?.uv ?: Double.MAX_VALUE,
                        feelLike = Double.MAX_VALUE,
                        visibility = Double.MAX_VALUE,
                        listHourTemperature = weatherRemote.forecast.forecastday[index].hour?.map { hour ->
                            HourTemperatureCached(
                                getHour(hour),
                                hour.temp_c ?: Double.MAX_VALUE,
                                hour.condition?.icon ?: ""
                            )
                        } ?: listOf()
                    )
                    list.add(weather)
                }
            }
        list.toTypedArray().let {
            weatherDao.saveWeather(*it)
        }
    }

    private fun getHour(hour: Hour): String {
        val hour = hour.time?.split(" ")
        return when {
            hour.isNullOrEmpty() -> {
                ""
            }
            hour.size < 2 -> {
                ""
            }
            else -> {
                hour[1]
            }
        }
    }
}
