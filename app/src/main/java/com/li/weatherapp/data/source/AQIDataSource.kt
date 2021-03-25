package com.li.weatherapp.data.source

import com.li.weatherapp.data.model.AQI
import com.li.weatherapp.data.source.utils.OnDataLoadCallback

interface AQIDataSource {
    fun getAQIData(lat: String, lon: String, callback: OnDataLoadCallback<AQI>)
}
