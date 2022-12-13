package com.weatherapplication.feature.searchcity.domain.usecase

import com.weatherapplication.feature.searchcity.base.SuspendingWorkInteractor
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(private val repository: SearchCityRepository) :
    SuspendingWorkInteractor<SearchCityUseCase.Params, List<SearchCity>>() {
    override suspend fun doWork(params: Params): List<SearchCity> = withContext(Dispatchers.IO) {
        if(params.query.isEmpty()){
            emptyList()
        } else {
            val remoteResults = repository.searchCity(params.query)
            when {
                remoteResults.isNotEmpty() -> remoteResults
                else -> emptyList()
            }
        }
    }

    data class Params(val query: String)
}
