package com.weatherapplication.data.models.weather.api

import com.google.gson.annotations.SerializedName

class WeatherModelRemote(
    @SerializedName("location") val location: Location? = null,
    @SerializedName("current") val current: Current? = null,
    @SerializedName("forecast") val forecast: Forecast? = null
)

data class Location(
    @SerializedName("name") val name: String?,
    @SerializedName("region") val region: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("tz_id") val tz_id: String?,
    @SerializedName("localtime_epoch") val localtime_epoch: Int?,
    @SerializedName("localtime") val localtime: String?
)

data class Current(
    @SerializedName("temp_c") val temp_c: Double?,
    @SerializedName("is_day") val is_day: Int?,
    @SerializedName("condition") val condition: Condition?,
    @SerializedName("wind_kph") val wind_kph: Double?,
    @SerializedName("pressure_mb") val pressure_mb: Double?,
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("cloud") val cloud: Int?,
    @SerializedName("feelslike_c") val feelslike_c: Double?,
    @SerializedName("uv") val uv: Int?
)

data class Forecast(
    @SerializedName("forecastday") val forecastday: List<Forecastday>
)

data class Forecastday(
    @SerializedName("date") val date: String?,
    @SerializedName("date_epoch") val date_epoch: Int?,
    @SerializedName("day") val day: Day?,
    @SerializedName("hour") val hour: List<Hour>?
)
data class Condition(
    @SerializedName("text") val text: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("code") val code: Int?
)

data class Day(
    @SerializedName("maxtemp_c") val maxtemp_c: Double?,
    @SerializedName("mintemp_c") val mintemp_c: Double?,
    @SerializedName("condition") val condition: Condition?
)

data class Hour(
    @SerializedName("time") val time: String?,
    @SerializedName("temp_c") var temp_c: Double?,
    @SerializedName("condition") val condition: Condition?
)

