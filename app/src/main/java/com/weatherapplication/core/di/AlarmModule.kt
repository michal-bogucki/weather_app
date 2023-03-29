package com.weatherapplication.core.di

import com.weatherapplication.core.alarm.ExactAlarms
import com.weatherapplication.core.alarm.ExactAlarmsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AlarmModule {
    @Singleton
    @Binds
    abstract fun bindExactAlarmInterface(exactAlarms: ExactAlarmsImpl): ExactAlarms
}
