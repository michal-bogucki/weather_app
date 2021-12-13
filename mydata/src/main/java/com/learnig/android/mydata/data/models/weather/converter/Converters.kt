package com.learnig.android.mydata.data.models.weather.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.learnig.android.mydata.data.models.weather.database.HourTemp
import com.learnig.android.mydata.data.models.weather.database.NextDay
import java.time.LocalDateTime


class Converters {
    @TypeConverter
    fun toDate(dateString: String?): LocalDateTime? {
        return if (dateString == null) {
            null
        } else {
            LocalDateTime.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDateTime?): String? {
        return date?.toString()
    }

    @TypeConverter
    internal fun fromListHourTempToDb(optionValues: List<HourTemp>): String {
        val type = object : TypeToken<List<HourTemp>>() {}.type
        return Gson().toJson(optionValues, type)
    }

    @TypeConverter
    internal fun fromDbToListHourTemp(optionValuesString: String?): List<HourTemp> {
        val type = object : TypeToken<List<HourTemp>>() {}.type
        return Gson().fromJson<List<HourTemp>>(optionValuesString, type)
    }

    @TypeConverter
    internal fun fromListNextDayToDb(optionValues: List<NextDay>): String {
        val type = object : TypeToken<List<NextDay>>() {}.type
        return Gson().toJson(optionValues, type)
    }

    @TypeConverter
    internal fun fromDbToListNextDay(optionValuesString: String?): List<NextDay> {
        val type = object : TypeToken<List<NextDay>>() {}.type
        return Gson().fromJson<List<NextDay>>(optionValuesString, type)
    }

}

