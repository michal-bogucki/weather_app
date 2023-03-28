package com.weatherapplication.core.alarm
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.weatherapplication.core.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

import java.util.*
import javax.inject.Inject
@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var exactAlarms: ExactAlarms

    override fun onReceive(context: Context, intent: Intent) {

    }


}