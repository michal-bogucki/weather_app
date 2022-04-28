package com.weatherapplication.core.data

interface Item {
    val id: Any
    override fun equals(other: Any?): Boolean
}
