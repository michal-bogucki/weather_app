package com.weatherapplication.core.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RescheduleAlarmsBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var exactAlarms: ExactAlarms
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action != null) {
            if (action == Intent.ACTION_BOOT_COMPLETED) {
                exactAlarms.rescheduleAlarm()
            }
        }
    }
}