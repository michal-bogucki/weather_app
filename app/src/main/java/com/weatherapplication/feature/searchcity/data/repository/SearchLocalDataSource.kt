package com.weatherapplication.feature.searchcity.data.repository

import com.weatherapplication.feature.searchcity.data.local.dao.SearchCityDao
import com.weatherapplication.feature.searchcity.data.local.model.SearchCityCached
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import javax.inject.Inject

class SearchLocalDataSource @Inject constructor(private val searchCityDao: SearchCityDao) {

    suspend fun saveChooseCitySearch(searchCity: SearchCity) = searchCityDao.saveSearchCity(SearchCityCached(searchCity))

    fun getHistorySearchCity() = searchCityDao.getAllCity().map { it.toSearchCity() }
    suspend fun deleteSearchCity(searchCity: SearchCity) {
        searchCityDao.deleteSearchCity(SearchCityCached(searchCity))
    }
}
