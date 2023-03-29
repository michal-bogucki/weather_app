package com.weatherapplication.feature.searchcity.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapplication.feature.searchcity.domain.usecase.DeleteChooseCityUseCase
import com.weatherapplication.feature.searchcity.domain.usecase.SearchCityUseCase
import com.weatherapplication.feature.searchcity.domain.usecase.UpdateChooseCityUseCase
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
    private val updateChooseCityUseCase: UpdateChooseCityUseCase,
    private val deleteChooseCityUseCase: DeleteChooseCityUseCase,
) : ViewModel() {
    private val searchQuery = MutableStateFlow("")
    private val errorMessage = MutableStateFlow("")
    val state: StateFlow<SearchCityContract> = combine(
        searchCityUseCase.flow,
        searchQuery,
        errorMessage,
    ) { searchList, searchQuery, errorMessage ->
        if (errorMessage.isEmpty()) {
            SearchCityContract.SearchCityState(
                searchText = searchQuery,
                actualSearchCityList = searchList.map { SearchCityDisplayable(it) },
                isHistoryList = searchList.any { it.isHistory } || searchList.isEmpty(),
            )
        } else {
            SearchCityContract.Error(errorMessage)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SearchCityContract.SearchCityState(),
    )

    init {
        viewModelScope.launch {
            searchQuery.debounce(300).onEach { query ->
                val job = launch(Dispatchers.IO) {
                    searchCityUseCase(SearchCityUseCase.Params(query))
                }
                job.join()
            }.catch { throwable ->
                errorMessage.value = throwable.message ?: "Error"
            }.collect()
        }
    }

    fun search(searchTerm: String) {
        searchQuery.value = searchTerm
    }

    fun saveUserChoose(searchCity: SearchCityDisplayable) {
        viewModelScope.launch {
            searchQuery.value = ""
            updateChooseCityUseCase.executeSync(UpdateChooseCityUseCase.Params(searchCity.toSearchCity()))
        }
    }

    fun deleteUserChoose(searchCity: SearchCityDisplayable) {
        viewModelScope.launch {
            deleteChooseCityUseCase.executeSync(DeleteChooseCityUseCase.Params(searchCity.toSearchCity()))
        }
    }
}
