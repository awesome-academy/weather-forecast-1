package com.li.weatherapp.ui.setting

import android.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.li.weatherapp.R
import com.li.weatherapp.base.BaseFragment
import com.li.weatherapp.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : BaseFragment() {

    override val layoutResource get() = R.layout.fragment_setting

    override fun setupViews() {
        setupNotificationSwitch()
        setupLanguageSpinner()
    }

    override fun setupData() {
    }

    override fun initActions() {
        buttonBackSetting.setOnClickListener {
            fragmentManager?.removeFragment(this@SettingFragment)
        }
        showNotificationAboutDialog()
    }

    private fun setupNotificationSwitch() {
    }

    private fun setupLanguageSpinner() {
        val languageList = listOf(
            resources.getString(R.string.text_language_english),
            resources.getString(R.string.text_language_vietnamese)
        )
        val arrayAdapter =
            context?.let { ArrayAdapter(it, R.layout.item_language_spinner, languageList) }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.apply {
            gravity = Gravity.END
            adapter = arrayAdapter
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
        }
    }

    private fun showNotificationAboutDialog() {
        textAbout.setOnClickListener {
            AlertDialog.Builder(context, R.style.AboutDialogTheme)
                .setTitle(resources.getString(R.string.title_about))
                .setMessage(resources.getString(R.string.text_about_message))
                .setNegativeButton(resources.getString(R.string.text_ok)) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }
}
