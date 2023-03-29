package com.weatherapplication.core.alarm

interface ExactAlarms {

    fun rescheduleAlarm()

    fun scheduleExactAlarm(hour: Int, minute: Int)

    fun clearExactAlarm()

    fun canScheduleExactAlarms(): Boolean
}