package com.weatherapplication.feature.searchcity.data.repository

import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import javax.inject.Inject

class SearchCityRepositoryImpl @Inject constructor(
    private val searchLocalDataSource: SearchLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchCityRepository {

    override fun searchCity(cityName: String) = searchRemoteDataSource.searchCity(cityName)

    override suspend fun saveChooseCitySearch(searchCity: SearchCity) = searchLocalDataSource.saveChooseCitySearch(
        searchCity
    )

    override fun getHistorySearchCity() = searchLocalDataSource.getHistorySearchCity()
    override suspend fun deleteSearchCity(searchCity: SearchCity) = searchLocalDataSource.deleteSearchCity(
        searchCity
    )
}
