package com.weatherapplication.feature.searchcity.data.api

import com.google.gson.annotations.SerializedName
import com.weatherapplication.feature.searchcity.domain.model.SearchCity

data class SearchCityRemote(
    @SerializedName("country") val countryName: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("name") val cityName: String
) {
    fun toSearchCity() = SearchCity(cityName, countryName, lat, lon, false)
}
