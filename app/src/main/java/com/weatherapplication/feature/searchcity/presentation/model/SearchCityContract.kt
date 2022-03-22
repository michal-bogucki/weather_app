package com.weatherapplication.feature.searchcity.presentation.model

import com.weatherapplication.core.data.ViewEvent
import com.weatherapplication.core.data.ViewState
import kotlinx.coroutines.flow.Flow


class SearchCityContract {
    data class SearchCityState(
        val isLoading: Boolean = false,
        val error: String = "",
        val historySearchCityList: List<SearchCityDisplayable> = emptyList(),
        val actualSearchCityList: List<SearchCityDisplayable> = emptyList(),
    ) : ViewState

    sealed class SearchCityEvent : ViewEvent {
        data class ChooseCity(val searchCity: SearchCityDisplayable) : SearchCityEvent()
        data class OnTextChange(val cityName: Flow<String>) : SearchCityEvent()
    }
}