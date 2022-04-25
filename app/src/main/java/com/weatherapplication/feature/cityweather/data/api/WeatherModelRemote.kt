package com.weatherapplication.feature.cityweather.data.api

import com.google.gson.annotations.SerializedName

class WeatherModelRemote(
    @SerializedName("current") val current: Current? = null,
    @SerializedName("forecast") val forecast: Forecast? = null
)

data class Current(
    @SerializedName("temp_c") val tempC: Int,
    @SerializedName("temp_f") val tempF: Double,
    @SerializedName("is_day") val isDay: Int,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("wind_mph") val windMph: Double,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("wind_degree") val windDegree: Int,
    @SerializedName("wind_dir") val windDir: String,
    @SerializedName("pressure_mb") val pressureMb: Int,
    @SerializedName("pressure_in") val pressureIn: Double,
    @SerializedName("precip_mm") val precipMm: Double,
    @SerializedName("precip_in") val precipIn: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("cloud") val cloud: Int,
    @SerializedName("feelslike_c") val feelslikeC: Double,
    @SerializedName("feelslike_f") val feelslikeF: Int,
    @SerializedName("vis_km") val visKm: Int,
    @SerializedName("vis_miles") val visMiles: Int,
    @SerializedName("uv") val uv: Int,
    @SerializedName("gust_mph") val gustMph: Double,
    @SerializedName("gust_kph") val gustKph: Double
)

data class Forecast(
    @SerializedName("forecastday") val forecastday: List<ForecastDay>
)

data class ForecastDay(
    @SerializedName("date") val date: String?,
    @SerializedName("date_epoch") val date_epoch: Int?,
    @SerializedName("day") val day: Day?,
    @SerializedName("hour") val hour: List<Hour>?,
    @SerializedName("astro") val astro: Astro,
)

data class Condition(
    @SerializedName("text") val text: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("code") val code: Int?
)

data class Day(
    @SerializedName("maxtemp_c") val maxtempC: Double,
    @SerializedName("maxtemp_f") val maxtempF: Double,
    @SerializedName("mintemp_c") val mintempC: Double,
    @SerializedName("mintemp_f") val mintempF: Double,
    @SerializedName("avgtemp_c") val avgtempC: Double,
    @SerializedName("avgtemp_f") val avgtempF: Double,
    @SerializedName("maxwind_mph") val maxwindMph: Double,
    @SerializedName("maxwind_kph") val maxwindKph: Double,
    @SerializedName("totalprecip_mm") val totalprecipMm: Double,
    @SerializedName("totalprecip_in") val totalprecipIn: Double,
    @SerializedName("avgvis_km") val avgvisKm: Int,
    @SerializedName("avgvis_miles") val avgvisMiles: Int,
    @SerializedName("avghumidity") val avghumidity: Int,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("uv") val uv: Int
)

data class Hour(
    @SerializedName("time") val time: String?,
    @SerializedName("temp_c") var temp_c: Double?,
    @SerializedName("condition") val condition: Condition?
)

data class Astro(
    @SerializedName("sunrise") val sunrise: String,
    @SerializedName("sunset") val sunset: String,

)
