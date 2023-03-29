package com.weatherapplication.feature.cityweather.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Tasks
import com.weatherapplication.feature.cityweather.domain.repository.LocationRepository
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) : LocationRepository {
    override val fusedLocationClient: FusedLocationProviderClient
        get() = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? = withContext(Dispatchers.IO) {
        try {
            return@withContext Tasks.await(fusedLocationClient.lastLocation)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return@withContext null
    }
}
