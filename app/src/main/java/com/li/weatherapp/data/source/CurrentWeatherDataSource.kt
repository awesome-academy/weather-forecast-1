package com.li.weatherapp.data.source

import com.li.weatherapp.data.model.CurrentWeather
import com.li.weatherapp.data.source.utils.OnDataLoadCallback

interface CurrentWeatherDataSource {
    fun getCurrentWeatherForecast(lat: String, lon: String, callback: OnDataLoadCallback<CurrentWeather>)
}
