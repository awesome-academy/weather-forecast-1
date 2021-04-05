package com.li.weatherapp.utils

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class LocationServiceUtils private constructor(var context: Context) {

    fun locationEnable(): Boolean {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun internetEnable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val netWorkCap = connectivityManager.getNetworkCapabilities(network) ?: return false
            return netWorkCap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                netWorkCap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                netWorkCap.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }
    }

    companion object {
        private var instance: LocationServiceUtils? = null
        fun getInstance(context: Context) =
            instance ?: LocationServiceUtils(context).also { instance = it }
    }
}
