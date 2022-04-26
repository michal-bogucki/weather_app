package com.weatherapplication.feature.cityweather.presentation

import androidx.lifecycle.viewModelScope
import com.weatherapplication.core.base.BaseViewModel
import com.weatherapplication.core.base.Resource
import com.weatherapplication.feature.cityweather.domain.usecase.GetCityWeatherUseCase
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCityWeatherUseCase: GetCityWeatherUseCase
) : BaseViewModel<WeatherContract.WeatherState, WeatherContract.WeatherEvent>() {

    fun getWeather(cityId: String) {
        getCityWeatherUseCase(cityId, viewModelScope) { result ->
            result.onSuccess { flowList ->
                viewModelScope.launch {
                    flowList.distinctUntilChanged().collect {
                        when (it) {
                            is Resource.Success -> {
                                it.data?.let {
                                    setState { state ->
                                        state.copy(
                                            isLoading = false,
                                            listWeatherData = it.map {
                                                WeatherDisplayable(
                                                    it
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                            is Resource.Loading -> {
                                setState { state -> state.copy(isLoading = true) }
                            }
                            is Resource.Error -> {
                                setState { state -> state.copy(error = it.error ?: "") }
                            }
                        }
                    }
                }
            }
            result.onFailure {
            }
        }
    }

    override fun setInitialState() = WeatherContract.WeatherState()

    override fun handleEvents(event: WeatherContract.WeatherEvent) {
    }
}
