package com.weatherapplication.feature.searchcity.domain.repository

import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import kotlinx.coroutines.flow.Flow

interface SearchCityRepository {
    fun searchCity(cityName: String): Flow<List<SearchCity>>
    fun getHistorySearchCity(): Flow<List<SearchCity>>
}