package com.li.weatherapp.ui.favorite

import com.li.weatherapp.data.model.CurrentWeather
import com.li.weatherapp.data.model.SearchedCity
import com.li.weatherapp.data.repository.CurrentWeatherRepository
import com.li.weatherapp.data.repository.SearchedCityRepository
import com.li.weatherapp.data.source.utils.OnDataLoadCallback
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test
import java.lang.Exception

class FavoritePresenterTest {

    private val view = mockk<FavoriteContact.View>(relaxed = true)
    private val favoriteRepository = mockk<SearchedCityRepository>()
    private val weatherRepository = mockk<CurrentWeatherRepository>()
    private val callback = slot<OnDataLoadCallback<List<SearchedCity>>>()
    private val callbackCurrent = slot<OnDataLoadCallback<CurrentWeather>>()
    private val presenter = FavoritePresenter(view, favoriteRepository, weatherRepository)

    @Test
    fun `testGetFavoriteCities return onSuccess`() {
        val favoriteCities = mutableListOf<SearchedCity>()
        every {
            favoriteRepository.getFavoriteCities(capture(callback))
        } answers {
            callback.captured.onSuccess(favoriteCities)
        }
        presenter.getFavoriteCities()
    }

    @Test
    fun `testGetFavoriteCities return onFail`() {
        val exception = Exception()
        every {
            favoriteRepository.getFavoriteCities(capture(callback))
        } answers {
            callback.captured.onFail(exception)
        }
        presenter.getFavoriteCities()
        verify {
            view.showMessage(exception.toString())
        }
    }

    @Test
    fun `getWeatherForecast return onSuccess`() {
        val currentWeather = mockk<CurrentWeather>()
        val favoriteCities = mutableListOf<SearchedCity>()
        every {
            weatherRepository.getCurrentWeatherForecast("", "", capture(callbackCurrent))
        } answers {
            callbackCurrent.captured.onSuccess(currentWeather)
        }
        presenter.getWeatherForecast(favoriteCities)
        repeat(favoriteCities.size) {
            verify {
                view.showWeatherForecast(currentWeather)
            }
        }

    }

    @Test
    fun `getWeatherForecast return onFail`() {
        val favoriteCities = mutableListOf<SearchedCity>()
        val exception = Exception()
        every {
            weatherRepository.getCurrentWeatherForecast("", "", capture(callbackCurrent))
        } answers {
            callbackCurrent.captured.onFail(exception)
        }
        presenter.getWeatherForecast(favoriteCities)
        repeat(favoriteCities.size) {
            verify {
                view.showMessage(exception.toString())
            }
        }
    }
}
