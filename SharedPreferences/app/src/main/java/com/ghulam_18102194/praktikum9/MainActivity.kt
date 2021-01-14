package com.ghulam_18102194.praktikum9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.ghulam_18102194.praktikum9.data.SettingModel
import com.ghulam_18102194.praktikum9.databinding.ActivityMainBinding
import com.ghulam_18102194.praktikum9.preferences.SettingPreference

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE = 100
    }
    private lateinit var settingModel: SettingModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var mSettingPreference: SettingPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.act = this
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.main_title)
        mSettingPreference = SettingPreference(this)
        showExistingPreference()
    }
    private fun showExistingPreference() {
        settingModel = mSettingPreference.getSetting()
        populateView(settingModel)
    }
    private fun populateView(settingModel: SettingModel) {
        if (settingModel.isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            delegate.applyDayNight()
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            delegate.applyDayNight()
        }
        binding.settingModel = settingModel
    }
    fun openSetting(){
        val intent = Intent(this@MainActivity, SettingPreferenceActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == SettingPreferenceActivity.RESULT_CODE) {
                showExistingPreference()
            }
        }
    }

}