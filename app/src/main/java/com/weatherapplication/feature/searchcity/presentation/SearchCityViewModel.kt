package com.weatherapplication.feature.searchcity.presentation

import androidx.lifecycle.viewModelScope
import com.weatherapplication.core.base.BaseViewModel
import com.weatherapplication.feature.searchcity.domain.usecase.ChooseCityUseCase
import com.weatherapplication.feature.searchcity.domain.usecase.SearchCityUseCase
import com.weatherapplication.feature.searchcity.domain.usecase.ShowHistorySearchCity
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityContract
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val searchCityUseCase: SearchCityUseCase,
    private val showHistorySearchCityUseCase: ShowHistorySearchCity,
    private val chooseCityUseCase: ChooseCityUseCase,
) : BaseViewModel<SearchCityContract.SearchCityState, SearchCityContract.SearchCityEvent>() {
    override fun setInitialState() = SearchCityContract.SearchCityState()

    init {
        showHistorySearch()
    }

    private fun showHistorySearch() {
        showHistorySearchCityUseCase(Unit, viewModelScope) { result ->
            result.onSuccess {
                setState { state ->
                    state.copy(
                        isLoading = false,
                        historySearchCityList = it.map { SearchCityDisplayable(it) }
                    )
                }
            }

            result.onFailure {
                setState { state -> state.copy(isLoading = false, error = it.message ?: "") }
            }
        }
    }

    override fun handleEvents(event: SearchCityContract.SearchCityEvent) {
        when (event) {
            is SearchCityContract.SearchCityEvent.ChooseCity -> {
                val searchCity = event.searchCity.toSearchCity()
                viewModelScope.launch(Dispatchers.IO) {
                    chooseCityUseCase.action(searchCity)
                }
            }
            is SearchCityContract.SearchCityEvent.OnTextChange -> {
                searchCityUseCase(event.cityName.debounce(200L), viewModelScope) { result ->
                    result.onSuccess {
                        viewModelScope.launch {
                            it.flowOn(Dispatchers.IO).map { list ->
                                list.map { SearchCityDisplayable(it) }
                            }.collect {
                                setState { state ->
                                    state.copy(
                                        isLoading = false,
                                        actualSearchCityList = it
                                    )
                                }
                            }
                        }
                    }
                    result.onFailure {
                        setState { state -> state.copy(error = it.message ?: "") }
                    }
                }
            }
        }
    }
}
