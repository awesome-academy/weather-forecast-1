package com.li.weatherapp.data.source.remote.api

import android.net.Uri
import com.li.weatherapp.BuildConfig

object APIQueries {
    fun queryDailyForecast(lat: String, lon: String) =
        Uri.Builder().scheme(APIConstants.SCHEME_HTTPS)
            .authority(APIConstants.OPENWEATHER_AUTHORITY_API)
            .appendPath(APIConstants.OPENWEATHER_CONTENT_ONE_CALL)
            .appendQueryParameter(APIConstants.OPENWEATHER_LAT, lat)
            .appendQueryParameter(APIConstants.OPENWEATHER_LON, lon)
            .appendQueryParameter(
                APIConstants.OPENWEATHER_EXCLUDE,
                APIConstants.OPENWEATHER_EXCLUDE_CURRENT_HOURLY_MINUTELY
            )
            .appendQueryParameter(APIConstants.OPENWEATHER_UNITS, APIConstants.OPENWEATHER_METRIC)
            .appendQueryParameter(APIConstants.OPENWEATHER_API_KEY, BuildConfig.OPENWEATHER_API_KEY)
            .toString()

    fun queryAQIForecast(lat: String, lon: String) =
        Uri.Builder().scheme(APIConstants.SCHEME_HTTPS)
            .authority(APIConstants.OPENWEATHER_AUTHORITY_API)
            .appendPath(APIConstants.OPENWEATHER_CONTENT_AIR_POLLUTION)
            .appendQueryParameter(APIConstants.OPENWEATHER_LAT, lat)
            .appendQueryParameter(APIConstants.OPENWEATHER_LON, lon)
            .appendQueryParameter(APIConstants.OPENWEATHER_API_KEY, BuildConfig.OPENWEATHER_API_KEY)
            .toString()
    fun queryCurrentWeatherForecast(lat: String, lon: String) =
        Uri.Builder().scheme(APIConstants.SCHEME_HTTPS)
            .authority(APIConstants.OPENWEATHER_AUTHORITY_API)
            .appendPath(APIConstants.OPENWEATHER_CONTENT_WEATHER)
            .appendQueryParameter(APIConstants.OPENWEATHER_LAT, lat)
            .appendQueryParameter(APIConstants.OPENWEATHER_LON, lon)
            .appendQueryParameter(APIConstants.OPENWEATHER_UNITS, APIConstants.OPENWEATHER_METRIC)
            .appendQueryParameter(APIConstants.OPENWEATHER_API_KEY, BuildConfig.OPENWEATHER_API_KEY)
            .toString()
}
