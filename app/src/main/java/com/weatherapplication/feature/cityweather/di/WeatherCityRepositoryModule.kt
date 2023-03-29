package com.weatherapplication.feature.cityweather.di

import com.weatherapplication.feature.cityweather.data.repository.WeatherCityRepositoryImpl
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class WeatherCityRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindWeatherCityRepositoryInterface(repository: WeatherCityRepositoryImpl): WeatherCityRepository
}
