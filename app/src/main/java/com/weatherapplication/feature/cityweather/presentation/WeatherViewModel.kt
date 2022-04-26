package com.weatherapplication.feature.cityweather.presentation

import androidx.lifecycle.viewModelScope
import com.weatherapplication.core.base.BaseViewModel
import com.weatherapplication.core.base.Resource
import com.weatherapplication.feature.cityweather.domain.usecase.GetCityWeatherUseCase
import com.weatherapplication.feature.cityweather.presentation.model.DataDisplayable
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCityWeatherUseCase: GetCityWeatherUseCase
) : BaseViewModel<WeatherContract.WeatherState, WeatherContract.WeatherEvent>() {
    var listWeather: MutableList<WeatherDisplayable> = mutableListOf()
    fun getWeather(cityId: String) {
        getCityWeatherUseCase(cityId, viewModelScope) { result ->
            result.onSuccess { flowList ->
                viewModelScope.launch {
                    flowList.distinctUntilChanged().collect { resource ->
                        when (resource) {
                            is Resource.Success -> {
                                resource.data?.let { list ->
                                    if (list.isNotEmpty()) {
                                        listWeather.clear()
                                        val element = list.map { WeatherDisplayable(it) }
                                        listWeather.addAll(element)
                                        setState { state ->
                                            state.copy(
                                                isLoading = false,
                                                weatherDisplayable = WeatherDisplayable(list[0]),
                                                listDate = list.map {
                                                    DataDisplayable(
                                                        it.date.hashCode(),
                                                        it.date, it.date == LocalDate.now()
                                                    )
                                                })
                                        }
                                    }
                                }
                            }
                            is Resource.Loading -> {
                                setState { state -> state.copy(isLoading = true) }
                            }
                            is Resource.Error -> {
                                setState { state -> state.copy(error = resource.error ?: "") }
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

    fun clickData(dataDisplayable: DataDisplayable) {
        setState { state ->
            state.copy(
                isLoading = false,
                weatherDisplayable = listWeather.find { it.date == dataDisplayable.date },
                listDate = listWeather.map {
                    DataDisplayable(
                        it.date.hashCode(),
                        it.date, it.date == dataDisplayable.date
                    )
                })
        }
    }
}
