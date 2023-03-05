package com.weatherapplication.core.network

interface NetworkStateProvider {
    fun isNetworkAvailable(): Boolean
}
