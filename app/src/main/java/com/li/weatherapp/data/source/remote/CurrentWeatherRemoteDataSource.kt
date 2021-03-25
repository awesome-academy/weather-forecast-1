package com.li.weatherapp.data.source.remote

import com.li.weatherapp.data.model.CurrentWeather
import com.li.weatherapp.data.source.CurrentWeatherDataSource
import com.li.weatherapp.data.source.remote.api.APIQueries
import com.li.weatherapp.data.source.remote.utils.RemoteAsyncTask
import com.li.weatherapp.data.source.remote.utils.makeNetworkCall
import com.li.weatherapp.data.source.utils.OnDataLoadCallback
import org.json.JSONObject

class CurrentWeatherRemoteDataSource private constructor() : CurrentWeatherDataSource {

    override fun getCurrentWeatherForecast(
        lat: String,
        lon: String,
        callback: OnDataLoadCallback<CurrentWeather>
    ) {
        RemoteAsyncTask(callback) {
            getCurrentWeatherForecast(lat, lon)
        }.execute()
    }

    private fun getCurrentWeatherForecast(lat: String, lon: String): CurrentWeather {
        val jsonString = makeNetworkCall(
            APIQueries.queryCurrentWeatherForecast(lat, lon)
        )
        return JSONObject(jsonString).let(::CurrentWeather)
    }

    companion object {
        private var instance: CurrentWeatherRemoteDataSource? = null
        fun getInstance() = instance ?: CurrentWeatherRemoteDataSource().also { instance = it }
    }
}
