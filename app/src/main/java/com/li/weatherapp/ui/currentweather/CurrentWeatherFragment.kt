package com.li.weatherapp.ui.currentweather

import com.li.weatherapp.R
import com.li.weatherapp.base.BaseFragment
import com.li.weatherapp.data.model.AQI
import com.li.weatherapp.data.model.CurrentWeather
import com.li.weatherapp.data.repository.AQIRepository
import com.li.weatherapp.data.repository.CurrentCityRepository
import com.li.weatherapp.data.repository.CurrentWeatherRepository
import com.li.weatherapp.data.source.local.CurrentCityLocalDataSource
import com.li.weatherapp.data.source.remote.AQIRemoteDataSource
import com.li.weatherapp.data.source.remote.CurrentWeatherRemoteDataSource
import com.li.weatherapp.ui.airquality.AirQualityFragment
import com.li.weatherapp.ui.setting.SettingFragment
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
    private var aqiDegree: AQI? = null

    override val layoutResource get() = R.layout.fragment_current_weather

    override fun setupViews() {
        showCurrentTime()
    }

    override fun setupData() {
        presenter = context?.let {
            CurrentWeatherPresenter(
                this,
                CurrentWeatherRepository.getInstance(CurrentWeatherRemoteDataSource.getInstance()),
                AQIRepository.getInstance(AQIRemoteDataSource.getInstance()),
                CurrentCityRepository.getInstance(
                    CurrentCityLocalDataSource.getInstance(
                        SharePreferenceHelper.getInstance(it)
                    )
                )
            )
        }
        presenter?.getCurrentLocation()
    }

    override fun initActions() {
        buttonAirInformation.setOnClickListener {
            aqiDegree?.let {
                fragmentManager?.replaceFragment(
                    R.id.frameMain,
                    AirQualityFragment.getInstance(it)
                )
            }
        }
        buttonSettingCurrentWeather.setOnClickListener {
            fragmentManager?.replaceFragment(R.id.frameMain, SettingFragment())
        }
    }

    override fun showAQIForecast(airQuality: AQI) {
        this.aqiDegree = airQuality
        textAqi.text = airQuality.aqi.toString()
        progressAirQuality.progress = airQuality.aqi
        textMeasure.text =
            context?.let { AirPollutionUtils.getAirPollutionTitle(it, airQuality.aqi) }
        textAirDescription.text =
            context?.let { AirPollutionUtils.getAirPollutionDescription(it, airQuality.aqi) }
    }

    override fun updateLocation(lat: String, lon: String, cityName: String) {
        presenter?.getCurrentWeatherForecast(lat, lon)
        presenter?.getAQI(lat, lon)
        textTitleCurrentWeather.text = cityName
    }

    override fun showMessage(data: Any) {
    }

    override fun showCurrentWeatherForecast(weather: CurrentWeather) {
        textCurrentTemper.text = weather.currentTemp.currentTemp.toInt().toString()
        textDescription.text = weather.currentWeather.description.capitalize()
        textTemperatureData.text = formatString(
            weather.currentTemp.min.toInt().toString(),
            weather.currentTemp.max.toInt().toString()
        )
        textWind.text =
            weather.wind.speed.toInt().toString() + Constants.DEFAULT_KILOMETER
        textWindGusts.text =
            weather.wind.degree.toInt().toString() + Constants.DEFAULT_KILOMETER
        textWindDegree.text =
            weather.currentTemp.humidity.toInt().toString() + Constants.DEFAULT_PERCENT
    }

    private fun showCurrentTime() {
        val currentDate =
            SimpleDateFormat(Constants.FULL_DATE_FORMAT, Locale.getDefault()).format(Date())
        textCurrentTime.text = currentDate
    }

    private fun formatString(min: String, max: String) =
        "$min ${Constants.DEFAULT_CELSIUS} - $max ${Constants.DEFAULT_CELSIUS}"
}
