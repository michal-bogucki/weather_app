package com.weatherapplication.feature.cityweather.presentation.view.weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.core.base.Resource
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.usecase.GetTodayWeatherUseCase
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherNewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTodayWeatherUseCase: GetTodayWeatherUseCase,
) : ViewModel() {
    private val cityId: String = savedStateHandle["cityId"]!!

    val state: StateFlow<WeatherContract> = weatherUiState(getTodayWeatherUseCase.flow).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WeatherContract.Loading,
    )

    private fun weatherUiState(getTodayWeatherUseCase: Flow<Resource<WeatherData>>) =
        getTodayWeatherUseCase.map { resources ->
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
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTodayWeatherUseCase(GetTodayWeatherUseCase.Params(cityId))
        }
    }
}
