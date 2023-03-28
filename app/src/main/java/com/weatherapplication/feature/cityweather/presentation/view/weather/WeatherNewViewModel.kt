package com.weatherapplication.feature.cityweather.presentation.view.weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.core.base.Resource
import com.weatherapplication.feature.cityweather.domain.usecase.GetTodayWeatherUseCase
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherNewViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val getTodayWeatherUseCase: GetTodayWeatherUseCase,
) : ViewModel() {
    var cityId: String? = savedStateHandle["cityId"]
    private val errorMessage = MutableStateFlow("")

    val state: StateFlow<WeatherContract> = combine(getTodayWeatherUseCase.flow, errorMessage) { resources, error ->
        when (resources) {
            is Resource.Error -> {
                WeatherContract.Error(resources.throwable)
            }
            is Resource.Loading -> {
                WeatherContract.Loading
            }
            is Resource.Success -> {
                val weather = WeatherDisplayable(resources.data)
                WeatherContract.Success(weather)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = WeatherContract.Loading,
    )

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            cityId = savedStateHandle["cityId"]
            getTodayWeatherUseCase(GetTodayWeatherUseCase.Params(cityId))


        }
    }
}
