package com.li.weatherapp.ui.currentweather

import android.annotation.SuppressLint
import com.li.weatherapp.R
import com.li.weatherapp.base.BaseFragment
import com.li.weatherapp.data.model.AQI
import com.li.weatherapp.data.model.CurrentWeather
import com.li.weatherapp.data.repository.AQIRepository
import com.li.weatherapp.data.repository.CurrentWeatherRepository
import com.li.weatherapp.data.source.remote.AQIRemoteDataSource
import com.li.weatherapp.data.source.remote.CurrentWeatherRemoteDataSource
import com.li.weatherapp.utils.*
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.android.synthetic.main.layout_air_quality.*
import kotlinx.android.synthetic.main.layout_air_quality.progressAirQuality
import kotlinx.android.synthetic.main.layout_temperature.*
import kotlinx.android.synthetic.main.layout_weather_detail.*
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeatherFragment : BaseFragment(), CurrentWeatherForecastContact.View {
    private var presenter: CurrentWeatherForecastContact.Presenter? = null

    override val layoutResource get() = R.layout.fragment_current_weather

    override fun setupViews() {
        showCurrentTime()
    }

    override fun setupData() {
        presenter = CurrentWeatherPresenter(
            this,
            CurrentWeatherRepository.getInstance(CurrentWeatherRemoteDataSource.getInstance()),
            AQIRepository.getInstance(AQIRemoteDataSource.getInstance())
        )
        context?.resources?.let {
            val lat = getString(R.string.text_test_lat)
            val lon = getString(R.string.text_test_lon)
            presenter?.getCurrentWeatherForecast(lat, lon)
            presenter?.getAQI(lat, lon)
        }
    }

    override fun initActions() {
    }

    @SuppressLint("SetTextI18n")
    override fun showCurrentWeatherForecast(weather: CurrentWeather) {
        textCurrentTemper.text = weather.currentTemp.currentTemp.toInt().toString()
        textDescription.text = weather.currentWeather.description.capitalize()
        textTemperatureData.text = formatString(
            weather.currentTemp.min.toInt().toString(), weather.currentTemp.max.toInt()
                .toString()
        )
        textWind.text =
            weather.wind.speed.toInt().toString() + context?.resources?.getString(R.string.text_km)
        textWindGusts.text =
            weather.wind.degree.toInt().toString() + context?.resources?.getString(R.string.text_km)
        textWindDegree.text =
            weather.currentTemp.humidity.toInt()
                .toString() + context?.resources?.getString(R.string.text_percent)

    }

    override fun showAQIForecast(aqi: AQI) {
        textAqi.text = aqi.aqi.toString()
        progressAirQuality.progress = aqi.aqi
        textMeasure.text =
            context?.let { AirPollutionUtils.getAirPollutionTitle(aqi.aqi, it) }
        textAirDescription.text =
            context?.let { AirPollutionUtils.getAirPollutionDescription(aqi.aqi, it) }
    }

    override fun showMessage(data: Any) {
    }

    private fun formatString(min: String, max: String) =
        min + " " + context?.resources?.getString(R.string.text_degree) + " - " + max + " " + context?.resources?.getString(
            R.string.text_degree
        )

    private fun showCurrentTime() {
        val currentDate =
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        textCurrentTime.text = currentDate

    }
}

