package com.weatherapplication.feature.cityweather.data.local.dao

import androidx.room.*
import com.weatherapplication.feature.cityweather.data.local.model.WeatherCached
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Update
    suspend fun updateWeather(weather: WeatherCached)

    @Query("SELECT * FROM WeatherCached WHERE cityName = :cityName")
    fun getWeatherById(cityName: String): Flow<List<WeatherCached>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeather(vararg weatherCached: WeatherCached)
}
