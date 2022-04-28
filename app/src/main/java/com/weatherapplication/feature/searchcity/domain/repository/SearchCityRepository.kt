package com.weatherapplication.feature.searchcity.domain.repository

import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import kotlinx.coroutines.flow.Flow

interface SearchCityRepository {
    fun searchCity(cityName: Flow<String>): Flow<List<SearchCity>>
    fun saveChooseCitySearch(searchCity: SearchCity): Long
    fun getHistorySearchCity(): List<SearchCity>
}
