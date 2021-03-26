package com.li.weatherapp.ui.main

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.li.weatherapp.R
import com.li.weatherapp.base.BaseActivity
import com.li.weatherapp.base.BaseFragment
import com.li.weatherapp.ui.currentweather.CurrentWeatherFragment
import com.li.weatherapp.ui.dailyforecast.DailyForecastFragment
import com.li.weatherapp.ui.news.NewsFragment
import com.li.weatherapp.utils.Constants.DRAWABLE_ZERO_BOUND
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_appbar.*

class MainActivity : BaseActivity() {

    private val currentWeatherFragment = CurrentWeatherFragment()
    private val hourlyForecastFragment = CurrentWeatherFragment()
    private val dailyForecastFragment = DailyForecastFragment()
    private val favoriteCitiesFragment = CurrentWeatherFragment()
    private val newsFragment = NewsFragment()

    private val onBottomNavigationItemSelect =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuCurrentWeather -> showFragment(currentWeatherFragment)
                R.id.menuHourly -> showFragment(hourlyForecastFragment)
                R.id.menuDaily -> showFragment(dailyForecastFragment)
                R.id.menuFavorite -> showFragment(favoriteCitiesFragment)
                R.id.menuNews -> {
                    showFragment(newsFragment)
                    textLocation.text = resources.getString(R.string.title_news)
                    textLocation.setCompoundDrawablesWithIntrinsicBounds(
                        DRAWABLE_ZERO_BOUND,
                        DRAWABLE_ZERO_BOUND,
                        DRAWABLE_ZERO_BOUND,
                        DRAWABLE_ZERO_BOUND
                    )
                }
            }
            true
        }

    override val layoutResource get() = R.layout.activity_main

    override fun initViews() {
        bottomNavigationView.apply {
            setOnNavigationItemSelectedListener(onBottomNavigationItemSelect)
            selectedItemId = R.id.menuCurrentWeather
        }
    }

    override fun initData() {
    }

    private fun showFragment(fragment: BaseFragment) =
        supportFragmentManager.apply {
            popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            beginTransaction()
                .replace(R.id.frameMain, fragment)
                .commit()
        }

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
