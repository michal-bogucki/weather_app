package com.weatherapplication.feature.cityweather.domain.repository

import android.location.Address
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient

interface LocationRepository {
    val fusedLocationClient: FusedLocationProviderClient
    suspend fun getCurrentLocation(): Location?
    suspend fun getAddress(): List<Address>?
}
