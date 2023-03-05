package com.weatherapplication.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat.getSystemService

class NetworkStateProviderImpl(private val context: Context) :
    NetworkStateProvider {

    private val connectivityManager by lazy { getSystemService(context, ConnectivityManager::class.java) }

    override fun isNetworkAvailable(): Boolean {
        val manager = connectivityManager ?: return false
        val capabilities = (manager.getNetworkCapabilities(manager.activeNetwork)) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
