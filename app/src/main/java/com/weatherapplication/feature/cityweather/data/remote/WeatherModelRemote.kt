package com.weatherapplication.feature.cityweather.data.remote

import com.google.gson.annotations.SerializedName
import com.weatherapplication.feature.cityweather.data.repository.createWeatherCached
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.searchcity.domain.model.SearchCity

class WeatherModelRemote(
    @SerializedName("current") val current: Current? = null,
    @SerializedName("forecast") val forecast: Forecast? = null,
) {
    fun toWeatherData(city: SearchCity): List<WeatherData> { // todo: trzeba to przemyśleć jeszcze raz!!!
        val list: MutableList<WeatherData> = mutableListOf()
        val todayWeather = createWeatherCached(city, this)
        list.add(todayWeather)
        val forecast_ = this.forecast?.forecastday
        if (forecast_ != null) {
            for ((index, weatherNext) in forecast_.withIndex()) {
                if (index != 0) {
                    val weather = createWeatherCached(city, forecast, index, weatherNext)
                    list.add(weather)
                }
            }
        }
        return list
    }
}

data class Current(
    @SerializedName("temp_c") val tempC: Double?,
    @SerializedName("temp_f") val tempF: Double?,
    @SerializedName("is_day") val isDay: Int?,
    @SerializedName("condition") val condition: Condition?,
    @SerializedName("wind_mph") val windMph: Double?,
    @SerializedName("wind_kph") val windKph: Double?,
    @SerializedName("wind_degree") val windDegree: Int?,
    @SerializedName("wind_dir") val windDir: String?,
    @SerializedName("pressure_mb") val pressureMb: Double?,
    @SerializedName("pressure_in") val pressureIn: Double?,
    @SerializedName("precip_mm") val precipMm: Double?,
    @SerializedName("precip_in") val precipIn: Double?,
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("cloud") val cloud: Int?,
    @SerializedName("feelslike_c") val feelslikeC: Double?,
    @SerializedName("feelslike_f") val feelslikeF: Double?,
    @SerializedName("vis_km") val visKm: Double?,
    @SerializedName("vis_miles") val visMiles: Double?,
    @SerializedName("uv") val uv: Double?,
    @SerializedName("gust_mph") val gustMph: Double?,
    @SerializedName("gust_kph") val gustKph: Double?,
)

data class Forecast(
    @SerializedName("forecastday") val forecastday: List<ForecastDay>,
)

data class ForecastDay(
    @SerializedName("date") val date: String?,
    @SerializedName("date_epoch") val date_epoch: Int?,
    @SerializedName("day") val day: Day?,
    @SerializedName("hour") val hour: List<Hour>?,
    @SerializedName("astro") val astro: Astro?,
)

data class Condition(
    @SerializedName("text") val text: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("code") val code: Int?,
)

data class Day(
    @SerializedName("maxtemp_c") val maxtempC: Double?,
    @SerializedName("maxtemp_f") val maxtempF: Double?,
    @SerializedName("mintemp_c") val mintempC: Double?,
    @SerializedName("mintemp_f") val mintempF: Double?,
    @SerializedName("avgtemp_c") val avgtempC: Double?,
    @SerializedName("avgtemp_f") val avgtempF: Double?,
    @SerializedName("maxwind_mph") val maxwindMph: Double?,
    @SerializedName("maxwind_kph") val maxwindKph: Double?,
    @SerializedName("totalprecip_mm") val totalprecipMm: Double?,
    @SerializedName("totalprecip_in") val totalprecipIn: Double?,
    @SerializedName("avgvis_km") val avgvisKm: Double?,
    @SerializedName("avgvis_miles") val avgvisMiles: Double?,
    @SerializedName("avghumidity") val avghumidity: Int?,
    @SerializedName("condition") val condition: Condition?,
    @SerializedName("uv") val uv: Double?,
)

data class Hour(
    @SerializedName("time") val time: String?,
    @SerializedName("temp_c") var temp_c: Double?,
    @SerializedName("condition") val condition: Condition?,
)

data class Astro(
    @SerializedName("sunrise") val sunrise: String?,
    @SerializedName("sunset") val sunset: String?,

)
