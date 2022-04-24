package com.weatherapplication.feature.cityweather.data.local.dao

import androidx.room.*
import com.weatherapplication.feature.cityweather.data.local.model.WeatherCached
import com.weatherapplication.feature.searchcity.data.local.model.SearchCityCached
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Update
    suspend fun updateWeather(weather: WeatherCached)

    @Query("SELECT * FROM WeatherCached WHERE cityName = :cityName")
    suspend fun getWeatherById(cityName: String): Flow<WeatherCached>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveWeather(vararg weatherCached :WeatherCached)
}
