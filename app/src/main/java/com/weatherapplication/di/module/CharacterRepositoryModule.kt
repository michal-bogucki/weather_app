package com.weatherapplication.di.module


import com.weatherapplication.data.repository.WeatherRepository
import com.weatherapplication.data.repository.WeatherRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class CharacterRepositoryModule {
    @ActivityRetainedScoped
    @Binds
    abstract fun bindCharacterRepositoryInterface(repository: WeatherRepository):WeatherRepositoryInterface
}