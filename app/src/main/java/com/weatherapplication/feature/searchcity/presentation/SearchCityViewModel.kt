package com.weatherapplication.feature.searchcity.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.feature.searchcity.domain.usecase.ChooseCityUseCase
import com.weatherapplication.feature.searchcity.domain.usecase.SearchCityUseCase
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityContract
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val searchCityUseCase: SearchCityUseCase,
    private val chooseCityUseCase: ChooseCityUseCase
) : ViewModel() {
    private val searchQuery = MutableStateFlow("")
    val state: StateFlow<SearchCityContract.SearchCityState> = combine(
        searchCityUseCase.flow,
        searchQuery
    ) { searchList, searchQuery ->
        SearchCityContract.SearchCityState(
            searchText = searchQuery,
            actualSearchCityList = searchList.map { SearchCityDisplayable(it) }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SearchCityContract.SearchCityState.Empty
    )

    init {
        viewModelScope.launch {
            searchQuery.debounce(300).onEach { query ->
                val job = launch {
                    searchCityUseCase(SearchCityUseCase.Params(query))
                }
                job.join()
            }
                .catch { throwable -> {} }
                .collect()
        }
    }

    fun search(searchTerm: String) {
        searchQuery.value = searchTerm
    }

    fun chooseCity(searchCity: SearchCityDisplayable) {
        viewModelScope.launch {
            searchQuery.value = ""
            chooseCityUseCase(searchCity.toSearchCity(), viewModelScope)
        }
    }
}
