package com.weatherapplication.feature.searchcity.data.repository

import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCityRepositoryImpl @Inject constructor() : SearchCityRepository {
    override fun searchCity(cityName: String): Flow<List<SearchCity>> {
        TODO("Not yet implemented")
    }

    override fun getHistorySearchCity(): Flow<List<SearchCity>> {
        TODO("Not yet implemented")
    }
}