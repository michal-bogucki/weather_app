package com.weatherapplication.core.base

sealed class ValueState<out T> {
    object Empty : ValueState<Nothing>()
    class Value<out T>(val value: T) : ValueState<T>()

    companion object {
        fun <T> initValueState(value: T?): ValueState<T> = when (value) {
            null -> Empty
            else -> Value(value)
        }
    }
}
