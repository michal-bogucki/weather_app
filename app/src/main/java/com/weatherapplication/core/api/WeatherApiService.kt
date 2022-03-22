package com.weatherapplication.core.api


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast.json")
    suspend fun getWeather(
        @Query("key") key: String, @Query("q") query: String, @Query("days") days: Int
    ): Response<WeatherModelRemote>


    companion object {
        const val API_URL = "https://api.weatherapi.com/v1/"
        const val KEY = "79b2663820af44d8b0f172109213011"
    }
}