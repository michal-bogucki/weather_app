package com.weatherapplication.core.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.weatherapplication.R
import com.weatherapplication.core.activity.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val EXACT_ALARM_INTENT_REQUEST_CODE_2 = 1998
const val EXACT_ALARM_INTENT_1246 = 1246
const val NOTIFICATION_CHANNEL_ID = "Weather Notifications"
const val NOTIFICATION_CHANNEL_GROUP = "Reminder"

@HiltWorker
class DownloadWeatherWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            showNotification(
                EXACT_ALARM_INTENT_REQUEST_CODE_2,
                EXACT_ALARM_INTENT_1246,
                "worker work"
            )
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun showNotification(requestCode: Int, alarmIntent: Int, title: String) {
        val intentStart = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            requestCode,
            intentStart,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText("majkel")
            .setSmallIcon(R.drawable.weather_icon)
            .setContentIntent(pendingIntent)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)

        val notification = notificationBuilder.build()

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null
        ) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_GROUP,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "notification channel"
                }
            )
        }

        notificationManager.notify(alarmIntent, notification)
    }
}
