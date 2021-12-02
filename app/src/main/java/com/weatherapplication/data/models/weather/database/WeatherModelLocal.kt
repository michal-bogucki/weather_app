package com.weatherapplication.data.models.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.weatherapplication.data.models.weather.converter.Converters
import com.weatherapplication.data.models.weather.database.WeatherModelLocal.Companion.TABLE_NAME
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
    companion object {
        const val TABLE_NAME = "weather"
    }
}

data class HourTemp(
    val time: String,
    val temp: Double,
    val conditionText: String,
    val conditionIcon: String
)

data class NextDay(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val conditionText: String,
    val conditionIcon: String,
    val temperatureList: List<HourTemp>
)