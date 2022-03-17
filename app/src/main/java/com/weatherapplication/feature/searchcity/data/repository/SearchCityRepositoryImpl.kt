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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
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

        val list: List<SearchCityRemote> = Gson().fromJson(
            jsonString, object : TypeToken<List<SearchCityRemote?>?>() {}.type
        )
        listCity = list.sortedBy { searchCityRemote -> searchCityRemote.cityName }
    }


    override fun searchCity(cityName: Flow<String>): Flow<List<SearchCity>> {
        return cityName.onStart { listCity }.flatMapLatest { name ->
            if (name.isNotEmpty()) {
                val foundPlace: List<SearchCity> = listCity.filter { it.cityName.lowercase().contains(name) }.map { it.toSearchCity() }
                flowOf(foundPlace)
            } else {
                val value = searchCityDao.getAllCity().map { list -> list.toSearchCity() }
                flowOf(value)
            }
        }
    }

    override fun saveChooseCitySearch(searchCity: SearchCity) =
        searchCityDao.saveSearchCity(SearchCityCached(searchCity))


    override fun getHistorySearchCity() = searchCityDao.getAllCity().map { it.toSearchCity() }

}
