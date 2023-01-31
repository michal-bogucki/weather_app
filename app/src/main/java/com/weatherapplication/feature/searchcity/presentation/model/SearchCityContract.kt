package com.weatherapplication.feature.searchcity.presentation.model

import com.weatherapplication.core.data.NavigationEvent
import com.weatherapplication.core.data.ViewEvent

class SearchCityContract {
    data class SearchCityState(
        val isLoading: Boolean = false,
        val error: String = "",
        val searchText: String = "",
        val historySearchCityList: List<SearchCityDisplayable> = emptyList(),
        val actualSearchCityList: List<SearchCityDisplayable> = emptyList()
    ) {
        companion object {
            val Empty = SearchCityState()
        }
    }

    sealed class SearchCityEvent : ViewEvent {
        data class ChooseCity(val searchCity: SearchCityDisplayable) : SearchCityEvent()
        data class OnTextChange(val cityName: String) : SearchCityEvent()
    }

    sealed class NavigationSearchEvent : NavigationEvent {
        object ShowCityWeather : NavigationSearchEvent()
    }
}
