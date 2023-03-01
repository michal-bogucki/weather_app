package com.weatherapplication.feature.cityweather.data.local.dao

import androidx.room.*
import com.weatherapplication.feature.cityweather.data.local.model.WeatherCached
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WeatherDao {
    @Update
    fun updateWeather(weather: WeatherCached)

    @Query("SELECT * FROM WeatherCached WHERE cityName = :cityName AND date = :now")
    fun getWeatherById(cityName: String, now: LocalDate): Flow<WeatherCached>

    @Query("SELECT * FROM WeatherCached WHERE cityName = :cityName AND date IN (:dateList)")
    fun getWeatherNextDay(cityName: String, dateList: List<LocalDate>): Flow<List<WeatherCached>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeather(vararg weatherCached: WeatherCached)
}
