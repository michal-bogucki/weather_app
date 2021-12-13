package com.learnig.android.mydata.data.models.search

import com.learnig.android.mydata.data.models.Item

data class SearchItem(
    override val id: Int = 0,
    val name: String,
    val lat: Double,
    val lon: Double
) : Item