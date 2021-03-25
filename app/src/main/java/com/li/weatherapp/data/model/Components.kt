package com.li.weatherapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class Components(
    val CODegree: Double,
    val SO2Degree: Double,
    val NO2Degree: Double,
    val PM10Degree: Double
) : Parcelable {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getDouble(CO_DEGREE),
        jsonObject.getDouble(SO2_DEGREE),
        jsonObject.getDouble(NO2_DEGREE),
        jsonObject.getDouble(PM10_DEGREE)
    )

    companion object {
        const val COMPONENTS_NAME = "components"
        const val CO_DEGREE = "co"
        const val SO2_DEGREE = "so2"
        const val NO2_DEGREE = "no2"
        const val PM10_DEGREE = "pm10"
    }
}
