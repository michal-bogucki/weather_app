package com.weatherapplication.feature.cityweather.data.remote

import com.weatherapplication.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast.json")
    suspend fun getWeather(
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("q") query: String,
        @Query("days") days: Int = 5
    ): Response<WeatherModelRemote>

    companion object {
        const val API_URL = "https://api.weatherapi.com/v1/"
    }
}
