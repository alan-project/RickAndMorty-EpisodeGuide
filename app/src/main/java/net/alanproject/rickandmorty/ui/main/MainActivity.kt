package net.alanproject.rickandmorty.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.domain.common.DEEPLINK_KEY
import net.alanproject.rickandmorty.R
import net.alanproject.rickandmorty.databinding.ActivityMainBinding
import net.alanproject.rickandmorty.ui.navigation.characters.CharacterListFragment
import net.alanproject.rickandmorty.ui.navigation.episodes.EpisodesFragment
import net.alanproject.rickandmorty.ui.navigation.info.InfoFragment
import net.alanproject.rickandmorty.ui.navigation.locations.LocationsFragment
import net.alanproject.rickandmorty.ui.navigation.quote.QuoteFragment
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewModel.apply {
            this.versionConfig.observe(this@MainActivity, { pair ->
                if (pair.first) {
                    onUpdateNeeded(pair.second)
                }
            })
        }
        binding.lifecycleOwner = this

        Timber.d("savedInstanceState: $savedInstanceState")
        initBottomNavListener(savedInstanceState == null)
        getExtra()
        viewModel.checkNewVersion()
    }


    private fun initBottomNavListener(isInitial: Boolean) {
         Timber.d("initBottomNavListener In")
        binding.bottomNavi.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_episodes -> {
                    Timber.d("menu_episodes in")
                    val currFragment: Fragment? =
                        supportFragmentManager.fragments.findLast { it.isVisible }
                    if (currFragment !is EpisodesFragment) {
                        Timber.d("menu_episodes clicked")
                        loadFragment(EpisodesFragment())
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.menu_characters -> {
                    if (!binding.bottomNavi.rootView.findViewById<View>(R.id.menu_characters).isSelected) {
                        loadFragment(CharacterListFragment())
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.menu_locations -> {
                    if (!binding.bottomNavi.rootView.findViewById<View>(R.id.menu_locations).isSelected) {
                        loadFragment(LocationsFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.menu_quote -> {
                    Timber.d("menu_quote in")
                    if (!binding.bottomNavi.rootView.findViewById<View>(R.id.menu_quote).isSelected) {
                        Timber.d("menu_quote clicked")
                        loadFragment(QuoteFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.menu_info -> {
                    Timber.d("menu_episodes in")
                    if (!binding.bottomNavi.rootView.findViewById<View>(R.id.menu_info).isSelected) {
                        Timber.d("menu_episodes clicked")
                        loadFragment(InfoFragment())
                    }
                    return@setOnItemSelectedListener true
                }

                else -> {
                    loadFragment(EpisodesFragment())
                    return@setOnItemSelectedListener true
                }
            }
        }

        if (isInitial) {
            binding.bottomNavi.selectedItemId = R.id.menu_episodes
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    //Deep Link
    private fun getExtra() {
        val requestPage = intent.getStringExtra(DEEPLINK_KEY)
        Timber.d("requestPage: $requestPage")
        if (requestPage.isNullOrBlank()) {
            return
        }

        binding.bottomNavi.selectedItemId = when (requestPage) {
            "episode" -> R.id.menu_locations
            else -> R.id.menu_episodes
        }
    }

    //remote config update
    private fun onUpdateNeeded(isMandatoryUpdate: Boolean) {
        Timber.d("onUpdateNeeded In")
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.update_app))
            .setCancelable(false)
            .setMessage(
                if (isMandatoryUpdate) getString(R.string.dialog_update_mandatory_available_message) else getString(
                    R.string.dialog_update_available_message
                )
            )
            .setPositiveButton(getString(R.string.update_now))
            { _, _ ->
                openAppOnPlayStore(this, null)
            }

        if (!isMandatoryUpdate) {
            dialogBuilder.setNegativeButton(getString(R.string.later)) { dialog, _ ->
                dialog?.dismiss()
            }.create()
        }
        val dialog: AlertDialog = dialogBuilder.create()
        dialog.show()
    }

    private fun openAppOnPlayStore(context: Context, packageName: String?) {
        var packageName = packageName
        if (packageName == null) {
            packageName = context.packageName
        }
        val uri = Uri.parse("market://details?id=$packageName")
        openURI(context, uri, "Play Store not found in your device")
    }

    private fun openURI(
        context: Context,
        uri: Uri?,
        errMsg: String?
    ) {
        val i = Intent(Intent.ACTION_VIEW, uri)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

        if (context.packageManager.queryIntentActivities(i, 0).size > 0) {
            context.startActivity(i)
        } else if (errMsg != null) {
            Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show()
        }
    }



    override fun onBackPressed() {
        with(binding.bottomNavi) {
            if (selectedItemId != R.id.menu_episodes) {
                selectedItemId = R.id.menu_episodes
            } else {
                super.onBackPressed()
            }
        }
    }
}