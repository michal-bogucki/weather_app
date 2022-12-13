package com.weatherapplication.feature.searchcity.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.feature.searchcity.domain.usecase.ChooseCityUseCase
import com.weatherapplication.feature.searchcity.domain.usecase.SearchCityUseCase
import com.weatherapplication.feature.searchcity.domain.usecase.ShowHistorySearchCity
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityContract.SearchCityState
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val searchCityUseCase: SearchCityUseCase,
    private val showHistorySearchCityUseCase: ShowHistorySearchCity,
    private val chooseCityUseCase: ChooseCityUseCase,
) : ViewModel() {
    private val searchQuery = MutableStateFlow("")

    val state: StateFlow<SearchCityState> = combine(
        searchCityUseCase.flow,
        showHistorySearchCityUseCase.flow,
        searchQuery,
    ) { searchList, historyList, searchQuery ->
        SearchCityState(
            searchText = searchQuery,
            historySearchCityList = historyList.map { SearchCityDisplayable(it) },
            actualSearchCityList = searchList.map { SearchCityDisplayable(it) }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SearchCityState.Empty
    )

    init {
        viewModelScope.launch {
            searchQuery.debounce(300).onEach { query ->
                val job = launch {
                    searchCityUseCase(SearchCityUseCase.Params(query))
                }
                job.invokeOnCompletion { }
                job.join()

            }
                .catch { throwable -> {} }
                .collect()
        }
    }

    fun search(searchTerm: String) {
        Timber.d("majkel $searchTerm")
        searchQuery.value = searchTerm
    }

    fun chooseCity(searchCity: SearchCityDisplayable) {
        viewModelScope.launch {
            chooseCityUseCase(searchCity.toSearchCity(), viewModelScope)
        }
    }
}
