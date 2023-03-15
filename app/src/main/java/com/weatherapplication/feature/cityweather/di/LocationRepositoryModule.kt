package com.weatherapplication.feature.cityweather.di

import com.weatherapplication.feature.cityweather.data.repository.LocationRepositoryImpl
import com.weatherapplication.feature.cityweather.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class LocationRepositoryModule {
    @ActivityRetainedScoped
    @Binds
    abstract fun bindLocationRepositoryInterface(repository: LocationRepositoryImpl): LocationRepository
}
