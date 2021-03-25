package com.li.weatherapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class CurrentTemp(
    val currentTemp: Double,
    val min: Double,
    val max: Double,
    val humidity: Double
) : Parcelable {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getDouble(TEMP_CURRENT),
        jsonObject.getDouble(TEMP_MIN),
        jsonObject.getDouble(TEMP_MAX),
        jsonObject.getDouble(HUMIDITY)
    )

    companion object {
        const val CURRENT_TEMP_NAME = "main"
        const val TEMP_CURRENT = "temp"
        const val TEMP_MIN = "temp_min"
        const val TEMP_MAX = "temp_max"
        const val HUMIDITY = "humidity"
    }
}
