package com.li.weatherapp.data.source.remote

import com.li.weatherapp.data.model.AQI
import com.li.weatherapp.data.source.AQIDataSource
import com.li.weatherapp.data.source.remote.api.APIQueries
import com.li.weatherapp.data.source.remote.utils.RemoteAsyncTask
import com.li.weatherapp.data.source.remote.utils.makeNetworkCall
import com.li.weatherapp.data.source.utils.OnDataLoadCallback
import org.json.JSONObject

class AQIRemoteDataSource private constructor() : AQIDataSource {

    override fun getAQIData(lat: String, lon: String, callback: OnDataLoadCallback<AQI>) {
        RemoteAsyncTask(callback) {
            getAQIData(lat, lon)
        }.execute()
    }

    private fun getAQIData(lat: String, lon: String): AQI {
        val jsonString = makeNetworkCall(
            APIQueries.queryAQIForecast(lat, lon)
        )
        return JSONObject(jsonString).let(::AQI)
    }

    companion object {
        private var instance: AQIRemoteDataSource? = null
        fun getInstance() = instance ?: AQIRemoteDataSource().also { instance = it }
    }
}
