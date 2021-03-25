package com.li.weatherapp.ui.main

import com.li.weatherapp.base.BasePresenter
import com.li.weatherapp.base.BaseView

interface CurrentCityContact {
    interface View : BaseView {
        fun updateView(lat:Double,lon:Double,cityName: String)
    }

    interface Presenter : BasePresenter {
        fun setLatitude(lat: Double)
        fun setLongitude(lon: Double)
        fun setCityName(cityName: String)
        fun getCurrentCity()
    }
}
