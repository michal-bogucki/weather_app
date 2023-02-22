package com.weatherapplication.feature.cityweather.presentation.view.weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.core.base.Resource
import com.weatherapplication.feature.cityweather.domain.usecase.GetTodayWeather
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherNewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTodayWeather: GetTodayWeather,
) : ViewModel() {
    private val cityId: String = savedStateHandle["cityId"]!!

    val state: StateFlow<WeatherContract> = getTodayWeather.flow.map { resources ->
        when (resources) {
            is Resource.Error -> {
                WeatherContract.Error(resources.throwable)
            }
            is Resource.Loading -> {
                WeatherContract.Loading
            }
            is Resource.Success -> {
                val weather = resources.data
                WeatherContract.Success(WeatherDisplayable(weather))
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = WeatherContract.Loading,
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTodayWeather(GetTodayWeather.Params(cityId))
        }
    }

//    ver 1
//    private val errorMessage = MutableStateFlow("")
//    private val loading = MutableStateFlow(false)
//    private val data = MutableStateFlow<WeatherDisplayable?>(null)
//    val state: StateFlow<WeatherContract.WeatherState> = combine(
//        loading,
//        errorMessage,
//        data,
//    ) { loading, error, weather ->
//        WeatherContract.WeatherState(error = error, isLoading = loading, weatherDisplayable = weather)
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(),
//        initialValue = WeatherContract.Empty,
//    )
//
//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            getTodayWeather(GetTodayWeather.Params(cityId))
//            getTodayWeather.flow.collect { resources ->
//                when (resources) {
//                    is Resource.Error -> {
//                        loading.value = false
//                        errorMessage.value = resources.error ?: ""
//                    }
//                    is Resource.Loading -> {
//                        loading.value = true
//                    }
//                    is Resource.Success -> {
//                        loading.value = false
//                        data.value = resources.data?.let { WeatherDisplayable(it) }
//                    }
//                }
//            }
//        }
//    }
// ver 2
//    val state: StateFlow<WeatherContract.WeatherState> = combine(
//        getTodayWeather.flow,
//        errorMessage,
//    ) { weather, error ->
//        when (weather) {
//            is Resource.Error -> {
//                WeatherContract.WeatherState(error = weather.error ?: "", isLoading = false)
//            }
//            is Resource.Loading -> {
//                WeatherContract.WeatherState(isLoading = true)
//            }
//            is Resource.Success -> {
//                //  WeatherContract.WeatherState(isLoading = true, weatherDisplayable = WeatherDisplayable(weather.data))
//                WeatherContract.WeatherState()
//            }
//        }
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(),
//        initialValue = WeatherContract.Empty,
//    )
}
