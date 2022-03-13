package com.weatherapplication.feature.searchcity.presentation.model

import com.weatherapplication.core.data.Item

data class SearchCityDisplayable(
    override val id: Int,
    val cityName: String,
    val countryName: String,
    val postalCode: String,
    val lat: Double,
    val lon: Double,
    val isHistory: Boolean,
) : Item {

}