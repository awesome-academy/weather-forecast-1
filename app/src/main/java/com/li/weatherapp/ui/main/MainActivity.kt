package com.li.weatherapp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.li.weatherapp.R
import com.li.weatherapp.base.BaseActivity
import com.li.weatherapp.base.BaseFragment
import com.li.weatherapp.data.repository.CurrentCityRepository
import com.li.weatherapp.data.source.local.CurrentCityLocalDataSource
import com.li.weatherapp.ui.currentweather.CurrentWeatherFragment
import com.li.weatherapp.ui.dailyforecast.DailyForecastFragment
import com.li.weatherapp.utils.SharePreferenceHelper
import com.li.weatherapp.utils.putDouble
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_appbar.*
import java.util.*

class MainActivity : BaseActivity(), LocationListener, CurrentCityContact.View {

    private val currentWeatherFragment = CurrentWeatherFragment()
    private val hourlyForecastFragment = CurrentWeatherFragment()
    private val dailyForecastFragment = DailyForecastFragment()
    private val favoriteCitiesFragment = CurrentWeatherFragment()
    private val newsFragment = CurrentWeatherFragment()
    private var locationManager: LocationManager? = null
    private var presenter: CurrentCityPresenter? = null

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
        bottomNavigationView.apply {
            setOnNavigationItemSelectedListener(onBottomNavigationItemSelect)
            selectedItemId = R.id.menuCurrentWeather
        }
        checkPermission()
    }

    override fun initData() {
        presenter = CurrentCityPresenter(
            this,
            CurrentCityRepository.getInstance(
                CurrentCityLocalDataSource.getInstance(
                    SharePreferenceHelper.getInstance(this)
                )
            )
        )
    }

    override fun updateView(lat: Double, lon: Double, cityName: String) {
        textLocation.text = cityName
    }


    override fun showMessage(data: Any) {
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
            finish()
        }
    }

    override fun onLocationChanged(location: Location) {
        var cityName: String? = ""
        val geo = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>
        addresses = geo.getFromLocation(
            location.latitude,
            location.longitude, MAX_RESULT
        )
        if (addresses.isNotEmpty()) {
            cityName = addresses[0].locality
            presenter?.setCityName(cityName)
            presenter?.setLatitude(location.latitude)
            presenter?.setLongitude(location.longitude)
        }
        presenter?.start()

    }

    private fun showFragment(fragment: BaseFragment) =
        supportFragmentManager.apply {
            popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            beginTransaction()
                .replace(R.id.frameMain, fragment)
                .commit()
        }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            MIN_TIME,
            MIN_DISTANCE,
            this
        )
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE
            )
        }
    }


    companion object {
        const val REQUEST_CODE = 1
        const val MIN_TIME = 5000L
        const val MIN_DISTANCE = 5f
        const val MAX_RESULT = 1
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
