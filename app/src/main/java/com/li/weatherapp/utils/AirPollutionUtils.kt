package com.li.weatherapp.utils

import android.content.Context
import com.li.weatherapp.R

object AirPollutionUtils {
    
    fun getAirPollutionTitle(aqiDegree: Int, context: Context) = when (aqiDegree) {
        in 0..19 -> context.resources.getString(R.string.title_wonderful)
        in 20..49 -> context.resources.getString(R.string.title_good)
        in 50..99 -> context.resources.getString(R.string.title_bad)
        in 100..149 -> context.resources.getString(R.string.title_harmful)
        in 150..249 -> context.resources.getString(R.string.title_very_harmful)
        else -> context.resources.getString(R.string.title_dangerous)
    }

    fun getAirPollutionDescription(aqiDegree: Int, context: Context) = when (aqiDegree) {
        in 0..19 -> context.resources.getString(R.string.text_wonderful_air_description)
        in 20..49 -> context.resources.getString(R.string.text_good_air_description)
        in 50..99 -> context.resources.getString(R.string.text_bad_air_description)
        in 100..149 -> context.resources.getString(R.string.text_harmful_air_description)
        in 150..249 -> context.resources.getString(R.string.text_very_harmful_air_description)
        else -> context.resources.getString(R.string.text_dangerous_air_description)
    }
}
