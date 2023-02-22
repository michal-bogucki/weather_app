package com.weatherapplication.feature.searchcity.data.local.dao

import androidx.room.*
import com.weatherapplication.feature.searchcity.data.local.model.SearchCityCached

@Dao
interface SearchCityDao {
    @Query("SELECT * FROM SearchCityCached")
    fun getAllCity(): List<SearchCityCached>

    @Update
    suspend fun updateSearchCityCached(music: SearchCityCached)

    @Query("SELECT * FROM SearchCityCached WHERE cityName = :cityName")
    fun getSearchCityById(cityName: String): SearchCityCached

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveSearchCity(vararg searchCityCached: SearchCityCached)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveSearchCity(searchCityCached: SearchCityCached): Long

    @Delete
    suspend fun deleteSearchCity(searchCityCached: SearchCityCached)
}
