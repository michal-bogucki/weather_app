package com.weatherapplication.feature.searchcity.domain.usecase

import android.database.sqlite.SQLiteException
import com.weatherapplication.feature.searchcity.base.SuspendingWorkInteractor
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(private val repository: SearchCityRepository) :
    SuspendingWorkInteractor<SearchCityUseCase.Params, List<SearchCity>>() {
    override suspend fun doWork(params: Params): List<SearchCity> = withContext(Dispatchers.IO) {
        val remoteResults = repository.searchCity(params.query)
        when {
            remoteResults.isNotEmpty() -> remoteResults
            else -> emptyList()
        }
    }

    data class Params(val query: String)
}
