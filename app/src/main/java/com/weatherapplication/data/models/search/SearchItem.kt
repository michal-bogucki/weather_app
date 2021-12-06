package com.weatherapplication.data.models.search

import com.weatherapplication.data.models.Item

data class SearchItem(
    override val id: Int = 0,
    val name: String,
    val lat: Double,
    val lon: Double
) : Item