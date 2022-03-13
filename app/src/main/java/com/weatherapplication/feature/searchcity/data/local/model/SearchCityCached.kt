package com.weatherapplication.feature.searchcity.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchCityCached(
    @PrimaryKey
    val id: Int,
    val cityName: String,
    val countryName: String,
    val postalCode: String,
    val lat: Double,
    val lon: Double,
    val isHistory: Boolean,
)