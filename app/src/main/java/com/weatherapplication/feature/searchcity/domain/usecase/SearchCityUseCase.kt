package com.weatherapplication.feature.searchcity.domain.usecase

import com.weatherapplication.core.base.UseCase
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchCityUseCase @Inject constructor(private val repository: SearchCityRepository) : UseCase<Flow<List<SearchCity>>, Flow<String>>() {
    override suspend fun action(params: Flow<String>) = repository.searchCity(params)
}