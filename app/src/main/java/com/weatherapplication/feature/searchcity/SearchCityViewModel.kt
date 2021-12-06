package com.weatherapplication.feature.searchcity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.data.models.search.SearchItem
import com.weatherapplication.data.repository.WeatherRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(private val repositoryModule: WeatherRepositoryInterface) :
    ViewModel() {


    var listCity: List<SearchItem> = mutableListOf()
    private val _city: MutableLiveData<Pair<List<SearchItem>, Boolean>> = MutableLiveData()
    val city: MutableLiveData<Pair<List<SearchItem>, Boolean>>
        get() = _city


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
                    SearchItem(model.id, model.city, model.lat, model.lon)
                }.toMutableList().distinctBy { search -> search.name }
                city.value = Pair(list, true)
            }
        }
    }


    init {
        mockHistoryData()
    }

    private fun getWeather(weatherId: Int, name: String, lat: Double, long: Double) {
        viewModelScope.launch {
            repositoryModule.getWeather(weatherId, name, lat, long).collect {}
        }
    }

    private fun mockHistoryData() {
        viewModelScope.launch {
            repositoryModule.getAllWeather().collect {
                if (it.isNullOrEmpty()) {
                    getWeather(0, "Warszawa", 52.237049, 19.944544)
                    getWeather(0, "Kraków", 50.049683, 19.944544)
                    getWeather(0, "Wrocław", 51.107883, 17.038538)
                }
            }
        }

    }


}