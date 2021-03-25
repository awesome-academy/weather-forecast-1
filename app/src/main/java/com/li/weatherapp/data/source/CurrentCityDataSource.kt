package com.li.weatherapp.data.source

interface CurrentCityDataSource {
    fun setLatitude(lat: Double)
    fun setLongitude(lon: Double)
    fun setCityName(cityName: String)
    fun getLatitude(): Double
    fun getLongitude(): Double
    fun getCityName(): String
}
