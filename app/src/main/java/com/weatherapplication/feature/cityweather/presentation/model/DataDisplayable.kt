package com.weatherapplication.feature.cityweather.presentation.model

import java.time.LocalDate

data class DataDisplayable(
    val id: Int,
    val date: LocalDate,
    val isClick: Boolean = false,
)
