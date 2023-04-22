package com.weatherapplication.core.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.weatherapplication.core.workmanager.DownloadWeatherWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var exactAlarms: ExactAlarms

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == alarmIntentAction) {
            val hourOfDay: Int = intent.getIntExtra(alarmHour, -1)
            val minute: Int = intent.getIntExtra(alarmMinute, -1)
            exactAlarms.scheduleExactAlarm(hourOfDay, minute)
        }
        startWorkManager(context)
    }

    private fun startWorkManager(context: Context) {
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
