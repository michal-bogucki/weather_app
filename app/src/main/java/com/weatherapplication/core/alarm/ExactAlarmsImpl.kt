package com.weatherapplication.core.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

const val EXACT_ALARM_INTENT_REQUEST_CODE = 1999

class ExactAlarmsImpl @Inject constructor(@ApplicationContext val context: Context) : ExactAlarms {
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun rescheduleAlarm() {
        scope.launch {
            if (canScheduleExactAlarms()) {
                scheduleExactAlarm(8, 0)
            } else {
                clearExactAlarm()
            }
        }.invokeOnCompletion {
            scope.cancel()
        }
    }

    override fun scheduleExactAlarm(hour: Int, minute: Int) {
        setExactAlarmSetExactAndAllowWhileIdle(convertToAlarmTimeMillis(hour, minute), hour, minute)
    }

    override fun clearExactAlarm() {
        val pendingIntent = createExactAlarmIntent(flag = PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
    }

    override fun canScheduleExactAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val canScheduleExactAlarms = alarmManager.canScheduleExactAlarms()
            canScheduleExactAlarms
        } else {
            true
        }
    }

    private fun setExactAlarmSetExactAndAllowWhileIdle(triggerAtMillis: Long, hour: Int, minute: Int) {
        val pendingIntent = createExactAlarmIntent(hour, minute, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    }

    private fun createExactAlarmIntent(hourOfDay: Int = -1, minute: Int = -1, flag: Int): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = "REMEMBER_WEATHER"
        if (hourOfDay != -1 && minute != -1) {
            intent.putExtra("hourOfDay", hourOfDay)
            intent.putExtra("minute", minute)
        }
        return PendingIntent.getBroadcast(
            context,
            EXACT_ALARM_INTENT_REQUEST_CODE,
            intent,
            flag,
        )
    }

    private fun convertToAlarmTimeMillis(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        val currentTimeMillis = calendar.timeInMillis
        val proposedTimeMillis = calendar.setHourAndMinute(hour, minute).timeInMillis

        val alarmTimeMillis: Long = if (proposedTimeMillis > currentTimeMillis) {
            proposedTimeMillis
        } else {
            proposedTimeMillis.plusOneDay()
        }

        return alarmTimeMillis
    }
}

fun Calendar.setHourAndMinute(hour: Int, minute: Int): Calendar {
    set(Calendar.HOUR_OF_DAY, hour)
    set(Calendar.MINUTE, minute)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)

    return this
}

fun Long.plusOneDay(): Long = this + 24 * 60 * 60 * 1000