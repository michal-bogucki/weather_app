package com.weatherapplication.feature.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.learnig.android.mydata.data.models.weather.database.WeatherModelLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao: BaseDao<WeatherModelLocal> {

    @Query("SELECT * FROM ${WeatherModelLocal.TABLE_NAME}")
    fun getAllWeather(): Flow<List<WeatherModelLocal>>

    @Query("SELECT * FROM ${WeatherModelLocal.TABLE_NAME} WHERE id = :id")
    fun getWeatherById(id: Int): Flow<WeatherModelLocal>

    @Query("SELECT * FROM weather ORDER BY id DESC LIMIT 1")
    fun getLastWeather(): Flow<WeatherModelLocal>

    @Transaction
    suspend fun insertOrUpdate(obj: WeatherModelLocal) {
        val id = insert(obj)
        if (id == -1L) update(obj)
    }


}