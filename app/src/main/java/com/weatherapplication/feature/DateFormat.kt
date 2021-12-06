package com.weatherapplication.feature

import java.time.format.DateTimeFormatter
import java.util.*

object DateFormat {
    val formatterDate: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd",
        Locale("en")
    )
    val formatterTime: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
}