package com.weatherapplication.feature.searchcity.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class SearchCity(
    val id: Int,
    val cityName: String,
    val countryName: String,
    val postalCode: String,
    val lat: Double,
    val lon: Double,
    val isHistory: Boolean,
)