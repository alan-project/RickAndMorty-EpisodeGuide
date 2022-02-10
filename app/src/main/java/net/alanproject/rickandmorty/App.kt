package net.alanproject.rickandmorty

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        initTheme()
        initFirebaseRemoteConfig()
    }
    private fun initTheme() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val isDarkMode = pref.getBoolean(getString(R.string.setting_dark_key), true)

        Timber.d("isDarkMode: $isDarkMode")
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        Timber.d("intTheme End")
    }

    private fun initFirebaseRemoteConfig() {

        // update interval
        val cacheExpiration: Long = 3600

        FirebaseApp.initializeApp(this)
        FirebaseRemoteConfig.getInstance().apply {
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(cacheExpiration)
                .build()
            setConfigSettingsAsync(configSettings)

            setDefaultsAsync(R.xml.remote_config_defaults)
            fetchAndActivate().addOnCompleteListener { task ->
                var updated = task.result
                if (task.isSuccessful) {
                    updated = task.result
                    Timber.d("Config params updated: $updated")
                } else {
                    Timber.d("Config params updated: $updated")
                }
            }
        }
    }
}