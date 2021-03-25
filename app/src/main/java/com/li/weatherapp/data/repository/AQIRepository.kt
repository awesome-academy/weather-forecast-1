package com.li.weatherapp.data.repository

import com.li.weatherapp.data.model.AQI
import com.li.weatherapp.data.source.AQIDataSource
import com.li.weatherapp.data.source.DailyDataSource
import com.li.weatherapp.data.source.utils.OnDataLoadCallback

class AQIRepository(val remote: AQIDataSource) : AQIDataSource {

    override fun getAQIData(lat: String, lon: String, callback: OnDataLoadCallback<AQI>) {
        remote.getAQIData(lat, lon, callback)
    }

    companion object {
        private var instance: AQIRepository? = null
        fun getInstance(remote: AQIDataSource) =
            instance ?: AQIRepository(remote).also { instance = it }
    }
}
