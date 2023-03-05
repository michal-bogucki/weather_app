package com.weatherapplication.core.di.module

import android.content.Context
import com.weatherapplication.core.network.NetworkStateProvider
import com.weatherapplication.core.network.NetworkStateProviderImpl
import com.weatherapplication.feature.cityweather.data.remote.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideWeatherService(): WeatherApiService = Retrofit.Builder()
        .baseUrl(WeatherApiService.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)

    @Singleton
    @Provides
    fun provideNetworkProvider(@ApplicationContext context: Context): NetworkStateProvider = NetworkStateProviderImpl(context)
}
