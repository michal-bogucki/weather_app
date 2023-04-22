package com.weatherapplication.feature.searchcity.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weatherapplication.feature.searchcity.data.api.SearchCityRemote
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(@ApplicationContext private val context: Context) {

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
            jsonString,
            object : TypeToken<List<SearchCityRemote?>?>() {}.type
        )
        listCity = list.sortedBy { searchCityRemote -> searchCityRemote.cityName }
    }

    fun searchCity(cityName: String): List<SearchCity> {
        return listCity.filter {
            it.cityName.lowercase().contains(cityName)
        }.map { it.toSearchCity() }
    }
}
