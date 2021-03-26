package com.li.weatherapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.li.weatherapp.R

fun ImageView.load(imageLink: String) {
    Glide.with(context)
        .load(imageLink)
        .error(R.drawable.ic_weather_error)
        .placeholder(R.drawable.ic_weather_place_holder)
        .into(this)
}
