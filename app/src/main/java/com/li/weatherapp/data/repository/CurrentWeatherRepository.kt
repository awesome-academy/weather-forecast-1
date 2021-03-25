package com.li.weatherapp.data.repository

import com.li.weatherapp.data.model.CurrentWeather
import com.li.weatherapp.data.source.CurrentWeatherDataSource
import com.li.weatherapp.data.source.utils.OnDataLoadCallback

class CurrentWeatherRepository private constructor(val remote: CurrentWeatherDataSource) :
    CurrentWeatherDataSource {

    override fun getCurrentWeatherForecast(
        lat: String,
        lon: String,
        callback: OnDataLoadCallback<CurrentWeather>
    ) {
        remote.getCurrentWeatherForecast(lat, lon, callback)
    }

    companion object {
        private var instance: CurrentWeatherRepository? = null
        fun getInstance(remote: CurrentWeatherDataSource) =
            instance ?: CurrentWeatherRepository(remote).also { instance = it }
    }
}
