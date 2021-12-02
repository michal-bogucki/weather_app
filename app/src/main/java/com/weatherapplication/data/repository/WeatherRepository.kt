package com.weatherapplication.data.repository

import com.weatherapplication.data.dao.WeatherDao
import com.weatherapplication.data.remoteapi.WeatherApiService
import javax.inject.Inject

interface WeatherRepositoryInterface{

}


class WeatherRepository @Inject constructor(val weatherApiService: WeatherApiService, val weatherDao: WeatherDao):WeatherRepositoryInterface {


}