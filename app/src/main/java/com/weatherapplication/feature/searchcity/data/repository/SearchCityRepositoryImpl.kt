package com.weatherapplication.feature.searchcity.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weatherapplication.feature.searchcity.data.api.SearchCityRemote
import com.weatherapplication.feature.searchcity.data.local.dao.SearchCityDao
import com.weatherapplication.feature.searchcity.data.local.model.SearchCityCached
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class SearchCityRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context, private val searchCityDao: SearchCityDao) :
    SearchCityRepository {

    init {
        mockCity()
    }

    private lateinit var listCity: List<SearchCityRemote>
    private fun mockCity() {
        var jsonString = ""
        try {
            jsonString = context.assets.open("pol_city.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

        val list: List<SearchCityRemote> = Gson().fromJson(jsonString, object : TypeToken<List<SearchCityRemote?>?>() {}.type)
        listCity = list.sortedBy { searchCityRemote -> searchCityRemote.cityName }
    }

    override suspend fun searchCity(cityName: String): List<SearchCity> {
       return listCity.filter { it.cityName.lowercase().contains(cityName) }.map { it.toSearchCity() }
    }

    override suspend fun saveChooseCitySearch(searchCity: SearchCity) = searchCityDao.saveSearchCity(SearchCityCached(searchCity))

    override fun getHistorySearchCity() = searchCityDao.getAllCity().map { it.map {  it.toSearchCity() }}
}
