package com.weatherapplication.feature.searchcity.domain.usecase

import com.weatherapplication.core.base.SubjectUseCase
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(private val repository: SearchCityRepository) :
    SubjectUseCase<SearchCityUseCase.Params, List<SearchCity>>() {

    data class Params(val query: String)

    override fun createObservable(params: Params): Flow<List<SearchCity>> {
        return flow {
            if (params.query.isEmpty()) {
                emitAll(repository.getHistorySearchCity())
            } else {
                val remoteResults = repository.searchCity(params.query)
                when {
                    remoteResults.isNotEmpty() -> emit(remoteResults)
                    else -> emit(emptyList())
                }
            }
        }
    }
}
