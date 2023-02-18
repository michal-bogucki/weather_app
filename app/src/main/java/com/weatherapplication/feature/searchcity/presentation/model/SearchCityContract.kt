package com.weatherapplication.feature.searchcity.presentation.model

class SearchCityContract {
    data class SearchCityState(
        val isLoading: Boolean = false,
        val error: String = "",
        val searchText: String = "",
        val actualSearchCityList: List<SearchCityDisplayable> = emptyList()
    ) {
        companion object {
            val Empty = SearchCityState()
        }
    }
}
