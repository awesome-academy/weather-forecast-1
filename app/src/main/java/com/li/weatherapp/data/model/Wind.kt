package com.li.weatherapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class Wind(
    val speed: Double,
    val degree: Double
) : Parcelable {
    constructor(jsonObject: JSONObject) : this(
        jsonObject.getDouble(WIND_SPEED),
        jsonObject.getDouble(WIND_DEGREE)
    )

    companion object {
        const val WIND_NAME = "wind"
        const val WIND_SPEED = "speed"
        const val WIND_DEGREE = "deg"
    }
}
