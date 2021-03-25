package com.li.weatherapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class CurrentWeather(
    val currentWeather: Weather,
    val currentTemp: CurrentTemp,
    val wind: Wind
) : Parcelable {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getJSONArray(WEATHER_NAME).getJSONObject(0).let(::Weather),
        jsonObject.getJSONObject(CurrentTemp.CURRENT_TEMP_NAME).let(::CurrentTemp),
        jsonObject.getJSONObject(Wind.WIND_NAME).let(::Wind)
    )

    companion object {
        const val WEATHER_NAME = "weather"
    }
}
