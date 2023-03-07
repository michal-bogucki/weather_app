package com.weatherapplication.core.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.weatherapplication.core.base.Resource
import com.weatherapplication.feature.cityweather.domain.repository.WeatherCityRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
class DownloadWeatherWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
//            withContext(Dispatchers.IO) {
//               // val lastCity = weatherCityRepository.getLastCity()
//                lastCity?.let {
//                    val weatherToday = weatherCityRepository.getWeatherToday(it)
//                    weatherToday.collect { resource ->
//                        when (resource) {
//                            is Resource.Error -> {
//                                Timber.d("majkel worker Error")
//                            }
//                            is Resource.Loading -> {
//                                Timber.d("majkel worker Loading")
//                            }
//                            is Resource.Success -> {
//                                Timber.d("majkel worker Success")
//                            }
//                        }
//                    }
//                }
//            }
            Timber.d("majkel worker Result.success()")
            Result.success()
        } catch (e: Exception) {
            Timber.d("majkel worker Result.failure()")
            Result.failure()
        }
    }
}
