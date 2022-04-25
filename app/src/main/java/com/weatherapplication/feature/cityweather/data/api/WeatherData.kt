package com.weatherapplication.feature.cityweather.data.api
import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("location")
    val location: Location,
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("alert")
    val alert: Alert
) {
    data class Location(
        @SerializedName("name")
        val name: String,
        @SerializedName("region")
        val region: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double,
        @SerializedName("tz_id")
        val tzId: String,
        @SerializedName("localtime_epoch")
        val localtimeEpoch: Int,
        @SerializedName("localtime")
        val localtime: String
    )

    data class Current(
        @SerializedName("temp_c")
        val tempC: Int,
        @SerializedName("temp_f")
        val tempF: Double,
        @SerializedName("is_day")
        val isDay: Int,
        @SerializedName("condition")
        val condition: Condition,
        @SerializedName("wind_mph")
        val windMph: Double,
        @SerializedName("wind_kph")
        val windKph: Double,
        @SerializedName("wind_degree")
        val windDegree: Int,
        @SerializedName("wind_dir")
        val windDir: String,
        @SerializedName("pressure_mb")
        val pressureMb: Int,
        @SerializedName("pressure_in")
        val pressureIn: Double,
        @SerializedName("precip_mm")
        val precipMm: Int,
        @SerializedName("precip_in")
        val precipIn: Int,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("cloud")
        val cloud: Int,
        @SerializedName("feelslike_c")
        val feelslikeC: Double,
        @SerializedName("feelslike_f")
        val feelslikeF: Int,
        @SerializedName("vis_km")
        val visKm: Int,
        @SerializedName("vis_miles")
        val visMiles: Int,
        @SerializedName("uv")
        val uv: Int,
        @SerializedName("gust_mph")
        val gustMph: Double,
        @SerializedName("gust_kph")
        val gustKph: Double
    ) {
        data class Condition(
            @SerializedName("text")
            val text: String,
            @SerializedName("icon")
            val icon: String,
            @SerializedName("code")
            val code: Int
        )
    }

    data class Forecast(
        @SerializedName("forecastday")
        val forecastday: List<Forecastday>
    ) {
        data class Forecastday(
            @SerializedName("date")
            val date: String,
            @SerializedName("date_epoch")
            val dateEpoch: Int,
            @SerializedName("day")
            val day: Day,
            @SerializedName("astro")
            val astro: Astro,
            @SerializedName("hour")
            val hour: List<Hour>
        ) {
            data class Day(
                @SerializedName("maxtemp_c")
                val maxtempC: Double,
                @SerializedName("maxtemp_f")
                val maxtempF: Double,
                @SerializedName("mintemp_c")
                val mintempC: Double,
                @SerializedName("mintemp_f")
                val mintempF: Double,
                @SerializedName("avgtemp_c")
                val avgtempC: Double,
                @SerializedName("avgtemp_f")
                val avgtempF: Double,
                @SerializedName("maxwind_mph")
                val maxwindMph: Double,
                @SerializedName("maxwind_kph")
                val maxwindKph: Double,
                @SerializedName("totalprecip_mm")
                val totalprecipMm: Double,
                @SerializedName("totalprecip_in")
                val totalprecipIn: Double,
                @SerializedName("avgvis_km")
                val avgvisKm: Int,
                @SerializedName("avgvis_miles")
                val avgvisMiles: Int,
                @SerializedName("avghumidity")
                val avghumidity: Int,
                @SerializedName("condition")
                val condition: Condition,
                @SerializedName("uv")
                val uv: Int
            ) {
                data class Condition(
                    @SerializedName("text")
                    val text: String,
                    @SerializedName("icon")
                    val icon: String,
                    @SerializedName("code")
                    val code: Int
                )
            }

            data class Astro(
                @SerializedName("sunrise")
                val sunrise: String,
                @SerializedName("sunset")
                val sunset: String,
                @SerializedName("moonrise")
                val moonrise: String,
                @SerializedName("moonset")
                val moonset: String,
                @SerializedName("moon_phase")
                val moonPhase: String,
                @SerializedName("moon_illumination")
                val moonIllumination: String
            )

            data class Hour(
                @SerializedName("time_epoch")
                val timeEpoch: Int,
                @SerializedName("time")
                val time: String,
                @SerializedName("temp_c")
                val tempC: Int,
                @SerializedName("temp_f")
                val tempF: Int,
                @SerializedName("is_day")
                val isDay: Int,
                @SerializedName("condition")
                val condition: Condition,
                @SerializedName("wind_mph")
                val windMph: Double,
                @SerializedName("wind_kph")
                val windKph: Double,
                @SerializedName("wind_degree")
                val windDegree: Int,
                @SerializedName("wind_dir")
                val windDir: String,
                @SerializedName("pressure_mb")
                val pressureMb: Int,
                @SerializedName("pressure_in")
                val pressureIn: Double,
                @SerializedName("precip_mm")
                val precipMm: Int,
                @SerializedName("precip_in")
                val precipIn: Int,
                @SerializedName("humidity")
                val humidity: Int,
                @SerializedName("cloud")
                val cloud: Int,
                @SerializedName("feelslike_c")
                val feelslikeC: Double,
                @SerializedName("feelslike_f")
                val feelslikeF: Double,
                @SerializedName("windchill_c")
                val windchillC: Double,
                @SerializedName("windchill_f")
                val windchillF: Double,
                @SerializedName("heatindex_c")
                val heatindexC: Int,
                @SerializedName("heatindex_f")
                val heatindexF: Int,
                @SerializedName("dewpoint_c")
                val dewpointC: Double,
                @SerializedName("dewpoint_f")
                val dewpointF: Double,
                @SerializedName("will_it_rain")
                val willItRain: Int,
                @SerializedName("chance_of_rain")
                val chanceOfRain: String,
                @SerializedName("will_it_snow")
                val willItSnow: Int,
                @SerializedName("chance_of_snow")
                val chanceOfSnow: String,
                @SerializedName("vis_km")
                val visKm: Int,
                @SerializedName("vis_miles")
                val visMiles: Int
            ) {
                data class Condition(
                    @SerializedName("text")
                    val text: String,
                    @SerializedName("icon")
                    val icon: String,
                    @SerializedName("code")
                    val code: Int
                )
            }
        }
    }

    class Alert
}
