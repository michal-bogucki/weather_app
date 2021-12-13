package com.weatherapplication.feature.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.weatherapplication.data.repository.WeatherRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepositoryInterface) :
    ViewModel() {

    var weatherId = 0

    private val _weather: MutableLiveData<com.learnig.android.mydata.data.remoteapi.State<com.learnig.android.mydata.data.models.weather.database.WeatherModelLocal>> = MutableLiveData()
    val weather: MutableLiveData<com.learnig.android.mydata.data.remoteapi.State<com.learnig.android.mydata.data.models.weather.database.WeatherModelLocal>>
        get() = _weather


    fun getWeather(weatherId: Int, name: String, lat: Double, long: Double) {
        viewModelScope.launch {
            weatherRepository.getWeather(weatherId, name, lat, long).map {
                com.learnig.android.mydata.data.remoteapi.State.fromResource(it)
            }.collect {
                _weather.postValue(it)
            }
        }
    }

}