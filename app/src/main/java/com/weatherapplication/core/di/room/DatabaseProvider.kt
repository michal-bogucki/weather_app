package com.weatherapplication.core.di.room

import android.app.Application
import com.weatherapplication.core.database.AppDatabase
import com.weatherapplication.feature.cityweather.data.local.dao.WeatherDao
import com.weatherapplication.feature.searchcity.data.local.dao.SearchCityDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseProvider {
    @Singleton
    @Provides
    fun provideDatabase(app: Application): AppDatabase {
        return AppDatabase.getDatabase(app)
    }

    @Singleton
    @Provides
    fun searchCityDao(db: AppDatabase): SearchCityDao {
        return db.searchCityDao()
    }

    @Singleton
    @Provides
    fun weatherDao(db: AppDatabase): WeatherDao {
        return db.weatherDao()
    }




}