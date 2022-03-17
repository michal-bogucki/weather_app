package com.weatherapplication.feature.searchcity.presentation.model

import com.weatherapplication.core.data.Item
import com.weatherapplication.feature.searchcity.domain.model.SearchCity

data class SearchCityDisplayable(
    override val id: String,
    val cityName: String,
    val countryName: String,
    val lat: Double,
    val lon: Double,
    val isHistory: Boolean,
) : Item {
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