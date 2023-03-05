package com.weatherapplication.feature.searchcity.domain.repository

import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import kotlinx.coroutines.flow.Flow

interface SearchCityRepository {
    fun searchCity(cityName: String): List<SearchCity>
    suspend fun saveChooseCitySearch(searchCity: SearchCity): Long
    fun getHistorySearchCity(): Flow<List<SearchCity>>
    suspend fun deleteSearchCity(searchCity: SearchCity)
}
