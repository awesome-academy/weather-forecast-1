package com.li.weatherapp.data.source.local

import com.li.weatherapp.data.source.CurrentCityDataSource
import com.li.weatherapp.utils.Constants
import com.li.weatherapp.utils.SharePreferenceHelper

class CurrentCityLocalDataSource private constructor(
    private val preferencesHelper: SharePreferenceHelper
) : CurrentCityDataSource {

    override fun setLatitude(lat: Double) {
        preferencesHelper.put(Constants.LATITUDE_KEY, lat)
    }

    override fun setLongitude(lon: Double) {
        preferencesHelper.put(Constants.LONGITUDE_KEY, lon)
    }

    override fun setCityName(cityName: String) {
        preferencesHelper.put(Constants.CITY_NAME_KEY, cityName)
    }

    override fun getLatitude() =
        preferencesHelper.get(Constants.LATITUDE_KEY, Constants.DEFAULT_DOUBLE)

    override fun getLongitude() =
        preferencesHelper.get(Constants.LONGITUDE_KEY, Constants.DEFAULT_DOUBLE)

    override fun getCityName() =
        preferencesHelper.get(Constants.CITY_NAME_KEY, Constants.DEFAULT_STRING)

    companion object {
        private var instance: CurrentCityLocalDataSource? = null
        fun getInstance(preferencesHelper: SharePreferenceHelper) =
            instance ?: CurrentCityLocalDataSource(preferencesHelper).also { instance = it }
    }
}
