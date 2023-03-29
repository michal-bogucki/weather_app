package com.weatherapplication.core.alarm
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.weatherapplication.core.activity.MainActivity
import com.weatherapplication.core.workmanager.DownloadWeatherWorker
import dagger.hilt.android.AndroidEntryPoint

import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var exactAlarms: ExactAlarms

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "REMEMBER_WEATHER") {
            val hourOfDay: Int = intent.getIntExtra("hourOfDay", -1)
            val minute: Int = intent.getIntExtra("minute", -1)
            exactAlarms.scheduleExactAlarm(hourOfDay, minute)

        }
            val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(false)
            .build()

        val work = OneTimeWorkRequestBuilder<DownloadWeatherWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(DownloadWeatherWorker::class.java.name, ExistingWorkPolicy.KEEP, work)
    }


}