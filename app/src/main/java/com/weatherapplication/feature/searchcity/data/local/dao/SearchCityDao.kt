package com.weatherapplication.feature.searchcity.data.local.dao

import androidx.room.*
import com.weatherapplication.feature.searchcity.data.local.model.SearchCityCached
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchCityDao {
    @Query("SELECT * FROM SearchCityCached")
    fun getAllCity(): Flow<List<SearchCityCached>>

    @Query("SELECT * FROM SearchCityCached WHERE isHistory = 1")
    fun getHistorySearchCity(): Flow<List<SearchCityCached>>

    @Update
    suspend fun updateSearchCityCached(music: SearchCityCached)

    @Query("SELECT * FROM SearchCityCached WHERE id = :id")
    suspend fun getMusicById(id: Int): SearchCityCached


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveMusic(vararg musicCached: SearchCityCached)
}
