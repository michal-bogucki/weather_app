package com.weatherapplication.core.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi

@HiltAndroidApp
@ExperimentalCoroutinesApi
class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
    }
}