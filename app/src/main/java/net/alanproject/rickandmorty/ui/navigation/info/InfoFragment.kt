package net.alanproject.rickandmorty.ui.navigation.info

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.rickandmorty.R
import timber.log.Timber


@AndroidEntryPoint
class InfoFragment : PreferenceFragmentCompat() {

    private lateinit var pref: SharedPreferences


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        pref = PreferenceManager.getDefaultSharedPreferences(requireContext())


        initActionListener()

    }

    private fun initActionListener() {

        findPreference<Preference>(getString(R.string.info_github_key))?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URI))
            startActivity(intent)
            true
        }


        findPreference<Preference>(getString(R.string.send_feedback))?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "plain/text"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_address)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.email_text))
            }
            startActivity(Intent.createChooser(intent, ""))
            true
        }

        findPreference<Preference>(getString(R.string.rating))?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(STORE_URI))
            startActivity(intent)
            true
        }


    }

    override fun onResume() {
        super.onResume()
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        pref.unregisterOnSharedPreferenceChangeListener(listener)
    }

    var listener =
        OnSharedPreferenceChangeListener { _, key ->
            Timber.d("key: $key")
            if (key == getString(R.string.setting_dark_key)) {
                val isDarkMode =
                    pref.getBoolean(getString(R.string.setting_dark_key), true)
                Timber.d("isDarkMode: $isDarkMode")
                if (isDarkMode) {
                    Timber.d("isDarkMode True")
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    Timber.d("isDarkMode False")
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

    companion object{
        const val GITHUB_URI = "https://github.com/alan-project"
        const val STORE_URI = "https://play.google.com/store/apps/details?id=net.alanproject.rickandmorty"
    }

}