package com.learnig.android.mydata.data.models.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.learnig.android.mydata.data.models.Item
import com.learnig.android.mydata.data.models.weather.converter.Converters
import com.learnig.android.mydata.data.models.weather.database.WeatherModelLocal.Companion.TABLE_NAME
import java.time.LocalDateTime

@TypeConverters(Converters::class)
@Entity(tableName = TABLE_NAME)
data class WeatherModelLocal(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    val city: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val temp: Double,
    val date: LocalDateTime,
    val tempFeelsLike: Double,
    val conditionText: String,
    val conditionIcon: String,
    val isDay: Boolean,
    val humidity: Int,
    val cloud: Int,
    val wind: Double,
    val pressure: Double,
    val temperatureList: List<HourTemp>,
    val nextDayWeather: List<NextDay>
) : Item {


    fun getConditionIconUrl(): String {
        return "https:" + conditionIcon.replace("64x64", "128x128")
    }

    fun getWindWithUnit(): String {
        return wind.toString() + WIND_UNIT
    }

    fun getHumidityWithUnit(): String {
        return humidity.toString() + HUMIDITY_UNIT
    }

    fun getPressureWithUnit(): String {
        return pressure.toString() + PRESSURE_UNIT
    }

    fun getCloudWithUnit(): String {
        return wind.toString() + CLOUD_UNIT
    }

    fun getTempWithUnit(): String {
        return temp.toString() + TEMP_UNIT
    }

    companion object {
        const val TABLE_NAME = "weather"
        const val WIND_UNIT = " km/h"
        const val HUMIDITY_UNIT = " %"
        const val PRESSURE_UNIT = " hPa"
        const val CLOUD_UNIT = " %"
        const val TEMP_UNIT = " °"
    }
}

data class HourTemp(
    val time: String,
    val temp: Double,
    val conditionText: String,
    val conditionIcon: String,
    override val id: Int
) : Item {
    fun getConditionIconUrl(): String {
        return "https:" + conditionIcon.replace("64x64", "128x128")
    }

    fun getTempWithUnit(): String {
        return temp.toString() + WeatherModelLocal.TEMP_UNIT
    }

}

data class NextDay(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val conditionText: String,
    val conditionIcon: String,
    override val id: Int
) : Item {
    fun getConditionIconUrl(): String {
        return "https:" + conditionIcon.replace("64x64", "128x128")
    }

    fun getMaxTempWithUnit(): String {
        return maxTemp.toString() + WeatherModelLocal.TEMP_UNIT
    }

    fun getMinTempWithUnit(): String {
        return minTemp.toString() + WeatherModelLocal.TEMP_UNIT
    }
}
