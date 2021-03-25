package com.li.weatherapp.ui.currentweather

import com.li.weatherapp.data.model.AQI
import com.li.weatherapp.data.model.CurrentWeather
import com.li.weatherapp.data.repository.AQIRepository
import com.li.weatherapp.data.repository.CurrentCityRepository
import com.li.weatherapp.data.repository.CurrentWeatherRepository
import com.li.weatherapp.data.source.utils.OnDataLoadCallback
import java.lang.Exception

class CurrentWeatherPresenter(
    private val view: CurrentWeatherForecastContact.View,
    private val weatherRepository: CurrentWeatherRepository,
    private val aqiRepository: AQIRepository
) : CurrentWeatherForecastContact.Presenter {

    override fun getCurrentWeatherForecast(lat: String, lon: String) {
        weatherRepository.getCurrentWeatherForecast(
            lat,
            lon,
            object : OnDataLoadCallback<CurrentWeather> {
                override fun onSuccess(data: CurrentWeather) {
                    view.showCurrentWeatherForecast(data)
                }

                override fun onFail(exception: Exception) {
                    view.showMessage(exception.message.toString())
                }
            })
    }

    override fun getAQI(lat: String, lon: String) {
        aqiRepository.getAQIData(lat, lon, object : OnDataLoadCallback<AQI> {
            override fun onSuccess(data: AQI) {
                view.showAQIForecast(data)
            }

            override fun onFail(exception: Exception) {
                view.showMessage(exception.message.toString())
            }
        })
    }

    override fun start() {
    }
}
