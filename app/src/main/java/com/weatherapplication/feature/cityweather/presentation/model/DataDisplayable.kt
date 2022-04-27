package com.weatherapplication.feature.cityweather.presentation.model

import com.weatherapplication.core.data.Item
import java.time.LocalDate

data class DataDisplayable(
    override val id: Int,
    val date: LocalDate,
    val isClick: Boolean = false
) : Item
