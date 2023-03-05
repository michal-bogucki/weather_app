package com.weatherapplication.core.network

import kotlinx.coroutines.flow.Flow

interface NetworkStateProvider {
    val isNetworkAvailableFlow: Flow<ConnectionState>
    fun isNetworkAvailable(): ConnectionState
    fun cleanLastState()
}
