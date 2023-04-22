package com.weatherapplication.feature.searchcity.domain.usecase

import com.weatherapplication.core.base.UseCase
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteChooseCityUseCase @Inject constructor(
    private val repository: SearchCityRepository
) : UseCase<DeleteChooseCityUseCase.Params>() {
    override suspend fun doWork(params: Params) {
        withContext(Dispatchers.IO) {
            repository.deleteSearchCity(params.searchCity)
        }
    }

    data class Params(val searchCity: SearchCity)
}
