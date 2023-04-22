package com.weatherapplication.feature.cityweather.presentation.view.forecast

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.core.base.Resource
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.usecase.GetNextDayWeatherUseCase
import com.weatherapplication.feature.cityweather.presentation.model.ForecastContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class ForecastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val getNextDayWeatherUseCase: GetNextDayWeatherUseCase
) : ViewModel() {
    val cityId: String? = savedStateHandle["cityId"]
    val state: StateFlow<ForecastContract> = weatherUiState(getNextDayWeatherUseCase.flow).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ForecastContract.Loading
    )

    private fun weatherUiState(getNextDayWeatherUseCase: Flow<Resource<List<WeatherData>>>) =
        getNextDayWeatherUseCase.map { resources ->
            when (resources) {
                is Resource.Error -> {
                    ForecastContract.Error(resources.throwable)
                }

                is Resource.Loading -> {
                    ForecastContract.Loading
                }

                is Resource.Success -> {
                    val weatherList = resources.data.map {
                        WeatherDisplayable(it)
                    }
                    ForecastContract.Success(weatherList)
                }
            }
        }

    init {
        viewModelScope.launch {
            getNextDayWeatherUseCase(GetNextDayWeatherUseCase.Params(cityId))
        }
    }
}
