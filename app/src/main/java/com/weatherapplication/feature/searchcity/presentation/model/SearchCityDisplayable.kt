package com.weatherapplication.feature.searchcity.presentation.model

import com.weatherapplication.feature.searchcity.domain.model.SearchCity

data class SearchCityDisplayable(
    val id: String,
    val cityName: String,
    val countryName: String,
    val lat: Double,
    val lon: Double,
    val isHistory: Boolean
) {
    constructor(searchCity: SearchCity) : this(
        searchCity.cityName,
        searchCity.cityName,
        searchCity.countryName,
        searchCity.lat,
        searchCity.lon,
        searchCity.isHistory
    )

    fun toSearchCity() = SearchCity(cityName, countryName, lat, lon, true)
}
