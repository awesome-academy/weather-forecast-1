package com.li.weatherapp.ui.dailyforecast

import com.li.weatherapp.data.model.Daily
import com.li.weatherapp.data.repository.DailyRepository
import com.li.weatherapp.data.source.utils.OnDataLoadCallback
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test

import org.junit.Assert.*
import java.lang.reflect.Array

class DailyForecastPresenterTest {

    private val view = mockk<DailyForecastContract.View>(relaxed = true)
    private val repository = mockk<DailyRepository>()
    private val presenter = DailyForecastPresenter(view, repository)
    private val callback = slot<OnDataLoadCallback<List<Daily>>>()

    @Test
    fun `get daily forecast return success`() {
        val dailyList = listOf<Daily>()
        val lat = "21"
        val lon = "105"
        every {
            repository.getDailyForecast(lat, lon, capture(callback))
        } answers {
            callback.captured.onSuccess(dailyList)
        }
        presenter.getDailyForecast(lat, lon)
        verify {
            view.showDailyForecast(dailyList)
        }
    }

    @Test
    fun `get daily forecast return fail`() {
        val exception = Exception()
        val lat = "21"
        val lon = "105"
        every {
            repository.getDailyForecast(lat, lon, capture(callback))
        } answers {
            callback.captured.onFail(exception)
        }
        presenter.getDailyForecast(lat, lon)
        verify {
            view.showMessage(exception.message.toString())
        }
    }
}
