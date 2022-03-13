package com.weatherapplication.feature.searchcity.presentation

import com.weatherapplication.core.base.BaseViewModel
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(

) : BaseViewModel<SearchCityContract.SearchCityState, SearchCityContract.SearchCityEvent>() {
    override fun setInitialState(): SearchCityContract.SearchCityState {
        TODO("Not yet implemented")
    }

    override fun handleEvents(event: SearchCityContract.SearchCityEvent) {
        TODO("Not yet implemented")
    }


}