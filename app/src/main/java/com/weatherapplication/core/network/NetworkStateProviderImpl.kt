package com.weatherapplication.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

class NetworkStateProviderImpl(private val context: Context) : NetworkStateProvider {

    private val connectivityManager by lazy { getSystemService(context, ConnectivityManager::class.java) }
    private var lastState: ConnectionState? = null

    override val isNetworkAvailableFlow = callbackFlow {

        val callback = NetworkCallback { connectionState ->
            if (lastState != connectionState) {
                lastState = connectionState
                trySend(connectionState)
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager?.registerNetworkCallback(networkRequest, callback)

        val currentState = getCurrentConnectivityState(connectivityManager)
        if (lastState != currentState) {
            lastState = currentState
            trySend(currentState)
        }

        awaitClose {
            cleanLastState()
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }

    private fun NetworkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                callback(ConnectionState.Available)
            }

            override fun onLost(network: Network) {
                callback(ConnectionState.Unavailable)
            }
        }
    }

    private fun getCurrentConnectivityState(
        connectivityManager: ConnectivityManager?,
    ): ConnectionState {
        val capabilities = (connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)) ?: return ConnectionState.Unavailable
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectionState.Available
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectionState.Available
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectionState.Available
            else -> ConnectionState.Unavailable
        }
    }

    override fun isNetworkAvailable(): ConnectionState {
        val manager = connectivityManager ?: return ConnectionState.Unavailable
        val capabilities = (manager.getNetworkCapabilities(manager.activeNetwork)) ?: return ConnectionState.Unavailable
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectionState.Available
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectionState.Available
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectionState.Available
            else -> ConnectionState.Unavailable
        }
    }

    override fun cleanLastState() {
        lastState = null
    }
}

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
