package com.weatherapplication.core.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.weatherapplication.core.workmanager.DownloadWeatherWorker


import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
@ExperimentalCoroutinesApi
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : Timber.DebugTree() {
            override fun log(
                priority: Int,
                tag: String?,
                message: String,
                t: Throwable?,
            ) {
                super.log(priority, "global_tag_$tag", message, t)
            }
        })

        WorkManager.initialize(this, workManagerConfiguration)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val work = PeriodicWorkRequestBuilder<DownloadWeatherWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(DownloadWeatherWorker::class.java.name, ExistingPeriodicWorkPolicy.KEEP, work)
    }
}
