package com.li.weatherapp.ui.weatherdetail

import com.li.weatherapp.data.model.History
import com.li.weatherapp.data.repository.HistoryRepository
import com.li.weatherapp.data.source.utils.OnDataLoadCallback
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test

class WeatherDetailPresenterTest {

    private val view = mockk<WeatherDetailContract.View>(relaxed = true)
    private val repository = mockk<HistoryRepository>()
    private val presenter = WeatherDetailPresenter(view, repository)
    private val callback = slot<OnDataLoadCallback<List<History>>>()

    @Test
    fun `get history list return success`() {
        val historyList = listOf<History>()
        val lat = "21"
        val lon = "105"
        every {
            repository.getHistory(lat, lon, capture(callback))
        } answers {
            callback.captured.onSuccess(historyList)
        }
        presenter.getHistory(lat, lon)
        verify {
            view.showHistory(historyList)
        }
    }

    @Test
    fun `get history list return fail`() {
        val exception = Exception()
        val lat = "21"
        val lon = "105"
        every {
            repository.getHistory(lat, lon, capture(callback))
        } answers {
            callback.captured.onFail(exception)
        }
        presenter.getHistory(lat, lon)
        verify {
            view.showMessage(exception.message.toString())
        }
    }
}
