package com.li.weatherapp.utils

import android.content.Context
import com.li.weatherapp.R

object AirPollutionUtils {

    fun getAirPollutionTitle(context: Context, aqiDegree: Int) = when (aqiDegree) {
        1 -> context.resources.getString(R.string.title_wonderful)
        2 -> context.resources.getString(R.string.title_good)
        3 -> context.resources.getString(R.string.title_bad)
        4 -> context.resources.getString(R.string.title_harmful)
        else -> context.resources.getString(R.string.title_very_harmful)
    }

    fun getAirPollutionDescription(context: Context, aqiDegree: Int) = when (aqiDegree) {
        1 -> context.resources.getString(R.string.text_wonderful_air_description)
        2 -> context.resources.getString(R.string.text_good_air_description)
        3 -> context.resources.getString(R.string.text_bad_air_description)
        4 -> context.resources.getString(R.string.text_harmful_air_description)
        else -> context.resources.getString(R.string.text_very_harmful_air_description)
    }

    fun getAirPollutionColor(context: Context, aqiDegree: Int) = when (aqiDegree) {
        1 -> getColor(context, R.color.chartreuse)
        2 -> getColor(context, R.color.coral)
        3 -> getColor(context, R.color.scarlet)
        4 -> getColor(context, R.color.rose)
        else -> getColor(context, R.color.shocking_pink)
    }

    private fun getColor(context: Context, color: Int) = context.resources.getColor(color)
}
