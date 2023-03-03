package com.weatherapplication.core.base

import com.weatherapplication.feature.cityweather.presentation.getUnitSymbol

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

fun getValue(value: ValueState<Any>, unit: String = ""): String {
    return when (value) {
        ValueState.Empty -> "-"
        is ValueState.Value -> value.value.toString()
    } + getUnitSymbol(unit)
}
