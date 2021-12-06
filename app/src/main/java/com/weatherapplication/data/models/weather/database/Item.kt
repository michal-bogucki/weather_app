package com.weatherapplication.data.models.weather.database

interface Item {
    val id: Int
    val conditionIcon: String
    fun getConditionIconUrl(): String {
        return "https:" + conditionIcon.replace("64x64", "128x128")
    }
}