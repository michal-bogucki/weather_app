package com.weatherapplication.feature.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.data.models.weather.database.WeatherModelLocal
import com.weatherapplication.data.remoteapi.State
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

    private val _weather: MutableLiveData<State<WeatherModelLocal>> = MutableLiveData()
    val weather: MutableLiveData<State<WeatherModelLocal>>
        get() = _weather


    fun getWeather(weatherId: Int, name: String, lat: Double, long: Double) {
        viewModelScope.launch {
            weatherRepository.getWeather(weatherId, name, lat, long).map {
                State.fromResource(it)
            }.collect {
                _weather.value = it
            }
        }
    }

}