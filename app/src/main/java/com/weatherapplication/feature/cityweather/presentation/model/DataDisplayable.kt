package com.weatherapplication.feature.cityweather.presentation.model

import com.weatherapplication.core.data.Item
import com.weatherapplication.feature.cityweather.domain.model.HourTemperature
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import java.time.LocalDate
import java.time.LocalDateTime

data class DataDisplayable(
    override val id: Int,
    val date: LocalDate,
    val isClick: Boolean = false
) : Item

