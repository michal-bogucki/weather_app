package com.weatherapplication.feature.searchcity.domain.usecase

import com.weatherapplication.feature.searchcity.base.SubjectInteractor
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class ShowHistorySearchCity @Inject constructor(private val repository: SearchCityRepository) : SubjectInteractor<Unit, List<SearchCity>>() {
    override fun createObservable(params: Unit): Flow<List<SearchCity>> {
        val historySearchCity = repository.getHistorySearchCity()
        Timber.d("majkel ${historySearchCity.toString()}")
        return historySearchCity
    }

}
