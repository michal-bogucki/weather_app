package com.weatherapplication.feature.searchcity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.learnig.android.mydata.data.models.search.SearchItem
import com.weatherapplication.data.repository.WeatherRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val repositoryModule: WeatherRepositoryInterface
) :
    ViewModel() {


    var listCity: List<SearchItem> = mutableListOf()
    private val _city: MutableLiveData<Pair<List<SearchItem>, Boolean>> = MutableLiveData()
    val city: MutableLiveData<Pair<List<SearchItem>, Boolean>>
        get() = _city


    fun mockListCity(jsonDataFromAsset: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            val list: List<SearchItem> = Gson().fromJson(
                jsonDataFromAsset, object : TypeToken<List<SearchItem?>?>() {}.type
            )

            listCity = list.sortedBy { it.name }

        }

    }


    fun getFromCityList(it: String) {
        viewModelScope.launch {
            if (listCity.isNotEmpty()) {
                val filterList = listCity.filter { item ->
                    item.name.lowercase().contains(it.lowercase())
                }
                _city.value = Pair(filterList, false)
            }
        }
    }

    fun showHistorySearch() {
        viewModelScope.launch {
            repositoryModule.getAllWeather().collect {
                val list: List<SearchItem> = it.map { model ->
                    SearchItem(
                        model.id,
                        model.city,
                        model.lat,
                        model.lon
                    )
                }.toMutableList().distinctBy { search -> search.name }
                city.value = Pair(list, true)
            }
        }
    }


    init {
        mockHistoryData()
    }

    private suspend fun getWeather(weatherId: Int, name: String, lat: Double, long: Double) {
        repositoryModule.getWeather(weatherId, name, lat, long).collect {}
    }

    private fun mockHistoryData() {
        viewModelScope.launch {
            repositoryModule.getAllWeather().collect {
                if (it.isNullOrEmpty()) {
                    withContext(Dispatchers.IO) {
                        getWeather(0, "Warszawa", 52.237049, 19.944544)
                    }

                }
            }
        }

    }


}