package com.li.weatherapp.data.repository

import com.li.weatherapp.data.source.CurrentCityDataSource

class CurrentCityRepository private constructor(
    private val local: CurrentCityDataSource
) : CurrentCityDataSource {

    override fun setLatitude(lat: Double) {
        local.setLatitude(lat)
    }

    override fun setLongitude(lon: Double) {
        local.setLongitude(lon)
    }

    override fun setCityName(cityName: String) {
        local.setCityName(cityName)
    }

    override fun getLatitude() = local.getLatitude()

    override fun getLongitude() = local.getLongitude()

    override fun getCityName() = local.getCityName()

    companion object {
        private var instance: CurrentCityRepository? = null
        fun getInstance(local: CurrentCityDataSource) =
            instance ?: CurrentCityRepository(local).also { instance = it }
    }
}
