package com.weatherapplication.feature.searchcity.presentation

import androidx.lifecycle.viewModelScope
import com.weatherapplication.core.base.BaseComposeViewModel
import com.weatherapplication.feature.searchcity.domain.usecase.ChooseCityUseCase
import com.weatherapplication.feature.searchcity.domain.usecase.SearchCityUseCase
import com.weatherapplication.feature.searchcity.domain.usecase.ShowHistorySearchCity
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityContract
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val searchCityUseCase: SearchCityUseCase,
    private val showHistorySearchCityUseCase: ShowHistorySearchCity,
    private val chooseCityUseCase: ChooseCityUseCase,
) : BaseComposeViewModel<SearchCityContract.SearchCityState, SearchCityContract.SearchCityEvent>() {
    override fun setInitialState() = SearchCityContract.SearchCityState()
    private val _search = MutableStateFlow("")
    private val search = _search.asStateFlow()

    fun searchCity(city: String) {
        _search.value = city
        setState {
            copy(
                searchText = city
            )
        }
    }

    init {
        showHistorySearch()
        searchListener()
    }

    fun searchListener() {
        searchCityUseCase(search.debounce(200L), viewModelScope) { result ->
            result.onSuccess {
                viewModelScope.launch {
                    it.flowOn(Dispatchers.IO).map { list ->
                        list.map { SearchCityDisplayable(it) }
                    }.collect {
                        setState {
                            copy(
                                isLoading = false,
                                actualSearchCityList = it
                            )
                        }
                    }
                }
            }
            result.onFailure {
                setState { copy(error = it.message ?: "") }
            }
        }
    }

    private fun showHistorySearch() {
        showHistorySearchCityUseCase(Unit, viewModelScope) { result ->
            result.onSuccess {
                setState {
                    copy(
                        isLoading = false,
                        historySearchCityList = it.map { SearchCityDisplayable(it) }
                    )
                }
            }

            result.onFailure {
                setState { copy(isLoading = false, error = it.message ?: "") }
            }
        }
    }


    override fun handleEvents(event: SearchCityContract.SearchCityEvent) {
        when (event) {
            is SearchCityContract.SearchCityEvent.ChooseCity -> {
                val searchCity = event.searchCity.toSearchCity()
                viewModelScope.launch {
                    chooseCityUseCase(searchCity, viewModelScope)
                }
                searchCity("")

            }
            is SearchCityContract.SearchCityEvent.OnTextChange -> {
                searchCity(event.cityName)
                setState {
                    copy(
                        searchText = event.cityName
                    )
                }
            }
        }
    }
}
