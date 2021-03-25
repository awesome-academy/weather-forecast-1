package com.li.weatherapp.ui.main

import com.li.weatherapp.data.repository.CurrentCityRepository

class CurrentCityPresenter(
    private val view: CurrentCityContact.View,
    private val repository: CurrentCityRepository
) : CurrentCityContact.Presenter {

    override fun setLatitude(lat: Double) {
        repository.setLatitude(lat)
    }

    override fun setLongitude(lon: Double) {
        repository.setLongitude(lon)
    }

    override fun setCityName(cityName: String) {
        repository.setCityName(cityName)
    }

    override fun getCurrentCity() {
        view.updateView(
            repository.getLatitude(),
            repository.getLongitude(),
            repository.getCityName()
        )
    }

    override fun start() {
        getCurrentCity()
    }
}
