package com.weatherapplication.feature.searchcity.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weatherapplication.feature.searchcity.domain.model.SearchCity

@Entity
data class SearchCityCached(
    @PrimaryKey
    val cityName: String,
    val countryName: String,
    val lat: Double,
    val lon: Double,
) {
    constructor(searchCity: SearchCity) : this(
        searchCity.cityName,
        searchCity.countryName,
        searchCity.lat,
        searchCity.lon
    )

    fun toSearchCity() = SearchCity(cityName, countryName, lat, lon, true)
}