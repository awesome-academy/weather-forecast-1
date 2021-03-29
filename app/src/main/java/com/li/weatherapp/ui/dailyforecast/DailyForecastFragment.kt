package com.li.weatherapp.ui.dailyforecast

import com.li.weatherapp.R
import com.li.weatherapp.base.BaseFragment
import com.li.weatherapp.data.model.Daily
import com.li.weatherapp.data.repository.DailyRepository
import com.li.weatherapp.data.source.remote.DailyRemoteDataSource
import com.li.weatherapp.ui.adapters.DailyForecastAdapter
import com.li.weatherapp.ui.setting.SettingFragment
import com.li.weatherapp.utils.replaceFragment
import com.li.weatherapp.utils.showToast
import kotlinx.android.synthetic.main.fragment_daily_forecast.*
import java.util.*

class DailyForecastFragment : BaseFragment(), DailyForecastContract.View {

    private var presenter: DailyForecastContract.Presenter? = null
    private val adapter = DailyForecastAdapter(this::dailyForecastItemClick)
    private val maxList = mutableListOf<Int>()
    private val minList = mutableListOf<Int>()

    override val layoutResource: Int = R.layout.fragment_daily_forecast

    override fun setupViews() {
        setupAdapter()
        showCurrentMonth()
    }

    override fun setupData() {
        presenter =
            DailyForecastPresenter(
                this,
                DailyRepository.getInstance(DailyRemoteDataSource.getInstance())
            )
        getForecastByLocation()
    }

    override fun initActions() {
        swipeRefreshDaily.setOnRefreshListener {
            swipeRefreshDaily.isRefreshing = false
            getForecastByLocation()
        }
        buttonSettingDailyForecast.setOnClickListener {
            fragmentManager?.replaceFragment(R.id.frameMain, SettingFragment())
        }
    }

    override fun showDailyForecast(dailyForecastList: List<Daily>) {
        for (daily in dailyForecastList) {
            maxList.add(daily.temp.max.toInt())
            minList.add(daily.temp.min.toInt())
        }
        maxList.max()?.let {
            adapter.max = it
        }
        minList.min()?.let {
            adapter.min = it
        }
        adapter.updateData(dailyForecastList)
    }

    override fun showMessage(data: Any) {
        context?.showToast(data.toString())
    }

    private fun setupAdapter() {
        recyclerViewDaily.adapter = adapter
    }

    private fun getForecastByLocation() {
        context?.resources?.let {
            val lat = getString(R.string.text_test_lat)
            val lon = getString(R.string.text_test_lon)
            presenter?.getDailyForecast(lat, lon)
        }
    }

    private fun dailyForecastItemClick(item: Daily) {
        val dailyBottomSheetFragment = DailyBottomSheetFragment.newInstance(item)
        dailyBottomSheetFragment.show(childFragmentManager, TAG)
    }

    private fun showCurrentMonth() {
        val calendar = Calendar.getInstance()
        textMonth.text = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
    }

    companion object {
        const val TAG = "DailyBottomSheet"
    }
}
