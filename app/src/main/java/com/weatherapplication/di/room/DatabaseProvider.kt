package com.weatherapplication.di.room

import android.app.Application
import com.learnig.android.mydata.data.dao.WeatherDao
import com.learnig.android.mydata.data.database.AppDatabase

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
    fun characterDao(db: AppDatabase): WeatherDao {
        return db.weatherDao()
    }




}