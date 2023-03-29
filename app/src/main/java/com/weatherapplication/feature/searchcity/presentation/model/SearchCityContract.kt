package com.weatherapplication.feature.searchcity.presentation.model

sealed interface SearchCityContract {
    data class SearchCityState(
        val searchText: String = "",
        val actualSearchCityList: List<SearchCityDisplayable> = emptyList(),
        val isHistoryList: Boolean = true,
    ) : SearchCityContract

    data class Error(val error: String) : SearchCityContract
    object Loading : SearchCityContract
}
