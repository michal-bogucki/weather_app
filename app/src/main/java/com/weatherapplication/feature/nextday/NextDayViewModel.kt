package com.weatherapplication.feature.nextday

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.weatherapplication.data.repository.WeatherRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class NextDayViewModel @Inject constructor (private val repositoryModule: WeatherRepositoryInterface) :
ViewModel() {
    private val _weather: MutableLiveData<com.learnig.android.mydata.data.models.weather.database.WeatherModelLocal> = MutableLiveData()
    val weather: MutableLiveData<com.learnig.android.mydata.data.models.weather.database.WeatherModelLocal>
    get() = _weather

    fun getWeather(id: Int) {
        viewModelScope.launch {
            repositoryModule.getWeatherById(id).collect {
                _weather.value = it
            }
        }
    }
}