package com.weatherapplication.data.repository

import android.location.Location
import com.weatherapplication.data.dao.WeatherDao
import com.weatherapplication.data.models.weather.api.WeatherModelRemote
import com.weatherapplication.data.models.weather.database.WeatherModelLocal
import com.weatherapplication.data.models.weather.transformer.WeatherTransformer
import com.weatherapplication.data.remoteapi.NetworkBoundRepository
import com.weatherapplication.data.remoteapi.Resource
import com.weatherapplication.data.remoteapi.WeatherApiService
import com.weatherapplication.data.remoteapi.WeatherApiService.Companion.KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.time.LocalDateTime
import javax.inject.Inject

interface WeatherRepositoryInterface {
    fun getWeather(
        weatherId: Int,
        name: String,
        lat: Double,
        long: Double
    ): Flow<Resource<WeatherModelLocal>>

    suspend fun getWeatherById(id: Int): Flow<WeatherModelLocal>

    suspend fun getAllWeather(): Flow<List<WeatherModelLocal>>
}

@ExperimentalCoroutinesApi
class WeatherRepository @Inject constructor(val weatherApiService: WeatherApiService, val weatherDao: WeatherDao):WeatherRepositoryInterface {
    override fun getWeather(
        weatherId: Int,
        name: String,
        lat: Double,
        long: Double
    ): Flow<Resource<WeatherModelLocal>> {
        return object : NetworkBoundRepository<WeatherModelLocal, WeatherModelRemote>() {
            override suspend fun saveRemoteData(response: WeatherModelRemote) {
                weatherDao.insertOrUpdate(WeatherTransformer.transform(response, weatherId, name))
            }

            override fun fetchFromLocal(): Flow<WeatherModelLocal> =
                if (weatherId == 0) weatherDao.getLastWeather()
                else weatherDao.getWeatherById(weatherId)

            override suspend fun fetchFromRemote(): Response<WeatherModelRemote> =
                weatherApiService.getWeather(KEY, "$lat,$long", 5)

            override fun shouldFetchRemote(data: WeatherModelLocal?): Boolean = when {
                data == null -> true
                changeLocation(data.lat, data.lon, lat, long, 3000) -> true
                testTime(data.date, LocalDateTime.now(), 60) -> true
                else -> false
            }

            override fun shouldFetchLocal(data: WeatherModelLocal?): Boolean = when {
                data == null -> false
                changeLocation(data.lat, data.lon, lat, long, 3000) -> false
                else -> true
            }
        }.asFlow()

    }

    override suspend fun getWeatherById(id: Int): Flow<WeatherModelLocal> =
        weatherDao.getWeatherById(id)

    override suspend fun getAllWeather(): Flow<List<WeatherModelLocal>> = weatherDao.getAllWeather()

    private fun changeLocation(
        latLast: Double,
        longLast: Double,
        lat: Double,
        long: Double,
        distanceInMeters: Int
    ): Boolean {
        val actualLocation = Location("")
        actualLocation.longitude = long
        actualLocation.latitude = lat
        val lastLocation = Location("")
        lastLocation.latitude = latLast
        lastLocation.longitude = longLast
        return actualLocation.distanceTo(lastLocation) >= distanceInMeters
    }

    fun testTime(lastDate: LocalDateTime, newDate: LocalDateTime, timeInMinutes: Int) =
        java.time.Duration.between(lastDate, newDate).toMinutes() >= timeInMinutes
}