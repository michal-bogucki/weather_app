package com.weatherapplication.core.data

interface Item {
    val id: Int

    override fun equals(other: Any?): Boolean
}
