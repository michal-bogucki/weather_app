package com.weatherapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weatherapplication.data.models.weather.database.WeatherModelLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao:BaseDao<WeatherModelLocal> {

    @Query("SELECT * FROM ${WeatherModelLocal.TABLE_NAME}")
    fun getAllCharacter(): Flow<List<WeatherModelLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListCharacter(list: List<WeatherModelLocal>)

    @Query("SELECT * FROM ${WeatherModelLocal.TABLE_NAME} WHERE id = :id")
    fun getCharacterById(id:Int): Flow<WeatherModelLocal>



}