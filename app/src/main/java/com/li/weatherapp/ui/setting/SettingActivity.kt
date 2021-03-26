package com.li.weatherapp.ui.setting

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.li.weatherapp.R
import com.li.weatherapp.base.BaseActivity
import com.li.weatherapp.utils.showToast
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    private val languageList = listOf("English", "Vietnamese")

    override val layoutResource get() = R.layout.activity_setting

    override fun initViews() {
        val arrayAdapter =
            ArrayAdapter(this, R.layout.item_spinner_language, languageList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.apply {
            onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
            adapter = arrayAdapter
        }
    }

    override fun initData() {
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, SettingActivity::class.java)
    }
}
