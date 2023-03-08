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
import com.weatherapplication.core.base.Resource
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

const val EXACT_ALARM_INTENT_REQUEST_CODE = 1999
const val NOTIFICATION_CHANNEL_ID = "Weather Notifications"
const val NOTIFICATION_CHANNEL_GROUP = "Reminder"

@HiltWorker
class DownloadWeatherWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val weatherCityRepository: WeatherCityRepository,
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                val lastCity = weatherCityRepository.getLastCity()
                lastCity?.let {
                    val weatherToday = weatherCityRepository.getWeatherToday(it)
                    weatherToday.collect { resource ->
                        when (resource) {
                            is Resource.Error -> {
                                Timber.i("majkel worker Error")
                            }
                            is Resource.Loading -> {
                                Timber.i("majkel worker Loading")
                            }
                            is Resource.Success -> {
                                Timber.i("majkel worker Success")
                            }
                        }
                    }
                }
                showNot()
            }
            Timber.d("majkel worker Result.success()")
            Result.success()
        } catch (e: Exception) {
            Timber.d("majkel worker Result.failure() ${e?.toString()}")
            Result.failure()
        }
    }

    private fun showNot() {
        val intentStart = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            EXACT_ALARM_INTENT_REQUEST_CODE,
            intentStart,
            PendingIntent.FLAG_IMMUTABLE,
        )
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("weather")
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
                    NotificationManager.IMPORTANCE_HIGH,
                ).apply {
                    description = "notification channel"
                },
            )
        }

        notificationManager.notify(1245, notification)
    }
}
