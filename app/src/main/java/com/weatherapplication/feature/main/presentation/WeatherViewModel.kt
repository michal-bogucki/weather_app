package com.weatherapplication.feature.main.presentation

import android.util.Log
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
class WeatherViewModel @Inject constructor(
) : BaseViewModel<SearchCityContract.SearchCityState, SearchCityContract.SearchCityEvent>() {
    override fun setInitialState() = SearchCityContract.SearchCityState()
    override fun handleEvents(event: SearchCityContract.SearchCityEvent) {
        TODO("Not yet implemented")
    }


}



