package com.weatherapplication.feature.searchcity.domain.repository

import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import kotlinx.coroutines.flow.Flow

interface SearchCityRepository {
    suspend fun searchCity(cityName: String): List<SearchCity>
    suspend fun saveChooseCitySearch(searchCity: SearchCity): Long
    fun getHistorySearchCity(): List<SearchCity>
}
