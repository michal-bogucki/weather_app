package com.weatherapplication.feature.searchcity.domain.usecase

import com.weatherapplication.core.base.UseCase
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import javax.inject.Inject

class ShowHistorySearchCity @Inject constructor(private val repository: SearchCityRepository) : UseCase<List<SearchCity>, Unit>() {
    override suspend fun action(params: Unit) = repository.getHistorySearchCity()
}
