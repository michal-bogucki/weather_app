package com.weatherapplication.feature.searchcity.di

import com.weatherapplication.feature.searchcity.data.repository.SearchCityRepositoryImpl
import com.weatherapplication.feature.searchcity.domain.repository.SearchCityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class SearchCityRepositoryModule {
    @ActivityRetainedScoped
    @Binds
    abstract fun bindSearchCityRepositoryInterface(repository: SearchCityRepositoryImpl): SearchCityRepository
}
