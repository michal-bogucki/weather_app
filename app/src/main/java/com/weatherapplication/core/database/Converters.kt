package com.weatherapplication.core.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weatherapplication.feature.cityweather.data.local.model.HourTemperatureCached
import java.time.LocalDate
import java.time.LocalDateTime

class Converters {

    @TypeConverter
    fun toDateTime(dateString: String?): LocalDateTime? {
        return if (dateString == null) {
            null
        } else {
            LocalDateTime.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateTimeString(date: LocalDateTime?): String? {
        return if (date == null) {
            null
        } else {
            date.toString()
        }
    }

    @TypeConverter
    fun toDate(dateString: String?): LocalDate? {
        return if (dateString == null) {
            null
        } else {
            LocalDate.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDate?): String? {
        return if (date == null) {
            null
        } else {
            date.toString()
        }
    }

    @TypeConverter
    fun toJson(data: List<HourTemperatureCached>): String = data.toJson()

    @TypeConverter
    fun fromJson(json: String): List<HourTemperatureCached> = json.fromJson()
}

inline fun <reified T> String.fromJson(): T {
    val jsonAdapter = object : TypeToken<T>() {}.type
    return Gson().fromJson(this, jsonAdapter)
}

inline fun <reified T> T.toJson(): String {
    val gson = Gson()
    return gson.toJson(this)
}
