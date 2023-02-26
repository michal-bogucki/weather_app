package com.weatherapplication.core.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@HiltAndroidApp
@ExperimentalCoroutinesApi
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : Timber.DebugTree() {
            /**
             * Override [log] to modify the tag and add a "global tag" prefix to it. You can rename the String "global_tag_" as you see fit.
             */
            override fun log(
                priority: Int,
                tag: String?,
                message: String,
                t: Throwable?,
            ) {
                super.log(priority, "global_tag_$tag", message, t)
            }
        })
    }
}
