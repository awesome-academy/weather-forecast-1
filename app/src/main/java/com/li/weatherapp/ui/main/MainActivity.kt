package com.li.weatherapp.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.li.weatherapp.R
import com.li.weatherapp.base.BaseActivity
import com.li.weatherapp.base.BaseFragment
import com.li.weatherapp.base.BaseView
import com.li.weatherapp.data.repository.CurrentCityRepository
import com.li.weatherapp.data.source.local.CurrentCityLocalDataSource
import com.li.weatherapp.ui.warning.WarningFragment
import com.li.weatherapp.ui.currentweather.CurrentWeatherFragment
import com.li.weatherapp.ui.dailyforecast.DailyForecastFragment
import com.li.weatherapp.ui.favorite.FavoriteFragment
import com.li.weatherapp.ui.hourly.HourlyForecastFragment
import com.li.weatherapp.ui.news.NewsFragment
import com.li.weatherapp.utils.Constants
import com.li.weatherapp.utils.LocationServiceUtils
import kotlinx.android.synthetic.main.activity_main.*
import com.li.weatherapp.utils.SharePreferenceHelper
import com.li.weatherapp.utils.showToast

class MainActivity : BaseActivity(), BaseView {

    private val currentWeatherFragment = CurrentWeatherFragment()
    private val hourlyForecastFragment = HourlyForecastFragment()
    private val dailyForecastFragment = DailyForecastFragment()
    private val favoriteCitiesFragment = FavoriteFragment()
    private val newsFragment = NewsFragment()
    private var locationProvider: FusedLocationProviderClient? = null
    private var presenter: CurrentCityContract.Presenter? = null
    private var isEnableGPS = false
    private var isEnableInternet = false

    private val onBottomNavigationItemSelect =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuCurrentWeather -> showFragment(currentWeatherFragment)
                R.id.menuHourly -> showFragment(hourlyForecastFragment)
                R.id.menuDaily -> showFragment(dailyForecastFragment)
                R.id.menuFavorite -> showFragment(favoriteCitiesFragment)
                R.id.menuNews -> showFragment(newsFragment)
            }
            true
        }

    override val layoutResource get() = R.layout.activity_main

    override fun initViews() {
    }

    override fun initActions() {
    }

    override fun initData() {
        presenter = CurrentCityPresenter(
            CurrentCityRepository.getInstance(
                CurrentCityLocalDataSource.getInstance(
                    SharePreferenceHelper.getInstance(this)
                )
            )
        )
        isEnableGPS = LocationServiceUtils.getInstance(this).locationEnable()
        isEnableInternet = LocationServiceUtils.getInstance(this).internetEnable()
        if (!isEnableGPS || !isEnableInternet) {
            showFragment(WarningFragment())
        } else {
            locationProvider = LocationServices.getFusedLocationProviderClient(this)
            getCurrentLocation()
        }
    }

    override fun showMessage(data: Any) {
        this.showToast(data.toString())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            showFragment(WarningFragment())
        }
    }

    private fun showFragment(fragment: BaseFragment) =
        supportFragmentManager.apply {
            popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            beginTransaction()
                .replace(R.id.frameMain, fragment)
                .commit()
        }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationProvider?.lastLocation?.addOnCompleteListener location@{ task ->
                val location: Location = task.result ?: return@location
                presenter?.apply {
                    if (location != null) {
                        setLatitude(location.latitude)
                        setLongitude(location.longitude)
                    } else {
                        setLatitude(Constants.DEFAULT_LATITUDE)
                        setLongitude(Constants.DEFAULT_LONGITUDE)
                    }
                }
                setUpNavigation()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE
            )
        }
    }

    private fun setUpNavigation() {
        bottomNavigationView.apply {
            setOnNavigationItemSelectedListener(onBottomNavigationItemSelect)
            selectedItemId = R.id.menuCurrentWeather
        }
    }

    companion object {
        const val REQUEST_CODE = 1

        fun getIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java)

        fun getIntentFromNotification(context: Context) =
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
    }
}
