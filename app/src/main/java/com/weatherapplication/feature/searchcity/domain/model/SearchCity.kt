package com.weatherapplication.feature.searchcity.domain.model

data class SearchCity(
    val cityName: String,
    val countryName: String,
    val lat: Double,
    val lon: Double,
    val isHistory: Boolean
)
