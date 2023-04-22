package com.weatherapplication.feature.cityweather.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Tasks
import com.weatherapplication.feature.cityweather.domain.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) : LocationRepository {
    override val fusedLocationClient: FusedLocationProviderClient
        get() = LocationServices.getFusedLocationProviderClient(context)

    override suspend fun getAddress(): List<Address>? {
        val currentLocation = getCurrentLocation()
        val geoCoder = Geocoder(context, Locale.getDefault())
        return if (currentLocation == null) {
            null
        } else {
            geoCoder.getFromLocation(
                currentLocation.latitude,
                currentLocation.longitude,
                1
            )
        }
    }

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
