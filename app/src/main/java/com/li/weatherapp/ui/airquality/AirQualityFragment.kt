package com.li.weatherapp.ui.airquality

import androidx.core.os.bundleOf
import com.li.weatherapp.R
import com.li.weatherapp.base.BaseFragment
import com.li.weatherapp.data.model.AQI
import com.li.weatherapp.utils.AirPollutionUtils
import com.li.weatherapp.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_air_quality.*
import kotlinx.android.synthetic.main.layout_air_quality.progressAirQuality
import kotlinx.android.synthetic.main.layout_air_quality_info.*

class AirQualityFragment : BaseFragment() {
    private var aqiDegree: AQI? = null

    override val layoutResource get() = R.layout.fragment_air_quality

    override fun setupViews() {
    }

    override fun setupData() {
        getInputData()
    }

    override fun initActions() {
        buttonBackAirQuality.setOnClickListener { fragmentManager?.removeFragment(AirQualityFragment()) }
    }

    private fun getInputData() {
        aqiDegree = arguments?.getParcelable<AQI>(BUNDLE_AQI_DEGREE)?.apply {
            progressAirQuality.progress = this.aqi
            textAqi.text = this.aqi.toString()
            textCO2Info.text = this.airComponents.CODegree.toInt().toString()
            textNO2Info.text = this.airComponents.NO2Degree.toInt().toString()
            textSO2Info.text = this.airComponents.SO2Degree.toInt().toString()
            textPM10Info.text = this.airComponents.PM10Degree.toInt().toString()
            context?.let {
                textAirQuality.text =
                    AirPollutionUtils.getAirPollutionTitle(it, this.aqi)
                textAirQualityDescription.text =
                    AirPollutionUtils.getAirPollutionDescription(it, this.aqi)
                viewAirQuality.setBackgroundColor(
                    AirPollutionUtils.getAirPollutionColor(
                        it,
                        this.aqi
                    )
                )
            }
        }
    }

    companion object {
        const val BUNDLE_AQI_DEGREE = "BUNDLE_AQI_DEGREE"

        fun getInstance(aqiDegree: AQI) = AirQualityFragment().apply {
            arguments = bundleOf(BUNDLE_AQI_DEGREE to aqiDegree)
        }
    }
}
