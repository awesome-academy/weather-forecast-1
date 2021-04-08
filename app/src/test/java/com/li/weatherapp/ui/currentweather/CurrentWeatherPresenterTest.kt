package com.li.weatherapp.ui.currentweather

import com.li.weatherapp.data.model.AQI
import com.li.weatherapp.data.model.CurrentWeather
import com.li.weatherapp.data.repository.AQIRepository
import com.li.weatherapp.data.repository.CurrentCityRepository
import com.li.weatherapp.data.repository.CurrentWeatherRepository
import com.li.weatherapp.data.source.utils.OnDataLoadCallback
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test
import java.lang.Exception

class CurrentWeatherPresenterTest {
    private val view = mockk<CurrentWeatherForecastContact.View>(relaxed = true)
    private val weatherRepository = mockk<CurrentWeatherRepository>()
    private val aqiRepository = mockk<AQIRepository>()
    private val currentCityRepository = mockk<CurrentCityRepository>()
    private val callbackCurrent = slot<OnDataLoadCallback<CurrentWeather>>()
    private val callbackAQI = slot<OnDataLoadCallback<AQI>>()
    private val presenter = CurrentWeatherPresenter(
        view,
        weatherRepository,
        aqiRepository,
        currentCityRepository
    )

    @Test
    fun `get current forecast return onSuccess`() {
        val currentWeather = mockk<CurrentWeather>()
        val lat = "21.0160611"
        val lon = "105.7817224"
        every {
            weatherRepository.getCurrentWeatherForecast(lat, lon, capture(callbackCurrent))
        } answers {
            callbackCurrent.captured.onSuccess(currentWeather)
        }
        presenter.getCurrentWeatherForecast(lat, lon)
        verify {
            view.showCurrentWeatherForecast(currentWeather)
        }
    }

    @Test
    fun `get current forecast return onFail exception`() {
        val exception = Exception()
        val lat = "21.0160611"
        val lon = "105.7817224"
        every {
            weatherRepository.getCurrentWeatherForecast(lat, lon, capture(callbackCurrent))
        } answers {
            callbackCurrent.captured.onFail(exception)
        }
        presenter.getCurrentWeatherForecast(lat, lon)
        verify {
            view.showMessage(exception.message.toString())
        }
    }

    @Test
    fun `getAQI return onSuccess`() {
        val aqi = mockk<AQI>()
        val lat = "21.0160611"
        val lon = "105.7817224"
        every {
            aqiRepository.getAQIData(lat, lon, capture(callbackAQI))
        } answers {
            callbackAQI.captured.onSuccess(aqi)
        }
        presenter.getAQI(lat, lon)
        verify {
            view.showAQIForecast(aqi)
        }
    }

    @Test
    fun `getAQI return onFail exception`() {
        val exception = Exception()
        val lat = "21.0160611"
        val lon = "105.7817224"
        every {
            aqiRepository.getAQIData(lat, lon, capture(callbackAQI))
        } answers {
            callbackAQI.captured.onFail(exception)
        }
        presenter.getAQI(lat, lon)
        verify {
            view.showMessage(exception.message.toString())
        }
    }
}
