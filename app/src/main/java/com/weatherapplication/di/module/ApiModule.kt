package com.weatherapplication.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.learnig.android.mydata.data.remoteapi.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

import retrofit2.converter.moshi.MoshiConverterFactory

import javax.inject.Singleton



@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideRickAndMortyService(): WeatherApiService = Retrofit.Builder()
            .baseUrl(WeatherApiService.API_URL)
            .addConverterFactory(
                    MoshiConverterFactory.create(
                            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    )
            )
            .build()
            .create(WeatherApiService::class.java)
}