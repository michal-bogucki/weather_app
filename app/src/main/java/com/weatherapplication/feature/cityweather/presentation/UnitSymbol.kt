package com.weatherapplication.feature.cityweather.presentation

fun getUnitSymbol(type: String) =
    when (type) {
        "temperature" -> "°C"
        "windSpeed" -> "m/s"
        "humidity" -> "%"
        "precipitation" -> "mm"
        "uvIndex" -> "uv"
        "visibility" -> "km"
        else -> ""
    }
