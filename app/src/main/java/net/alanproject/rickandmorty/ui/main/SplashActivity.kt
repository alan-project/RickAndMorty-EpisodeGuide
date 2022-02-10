package net.alanproject.rickandmorty.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.domain.common.DEEPLINK_KEY
import net.alanproject.rickandmorty.R
import net.alanproject.rickandmorty.databinding.ActivitySplashBinding
import timber.log.Timber


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        onReceiveDynamicLink()
    }

    private fun onReceiveDynamicLink() {
        var requestPage:String? = null
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null

                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                if (deepLink != null) {
                    Timber.d("Here's the deep link URL: $deepLink")
                    requestPage = deepLink.getQueryParameter(DEEPLINK_KEY)
                    Timber.d("currentPage: $requestPage")
                }
            }
            .addOnFailureListener(this) { e ->
                Timber.e("getDynamicLink:onFailure", e)
            }
            .addOnCompleteListener {
                initSplashImage(requestPage)
            }
    }


    private fun initSplashImage(requestPage: String? = null) {
        Timber.d("GLIDE")
        Glide.with(this)
            .load(R.drawable.main_splash)
            .fitCenter()
            .into(binding.ivSplash)


        binding.ivSplash.alpha = 0f
        binding.ivSplash.animate().setDuration(1000).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
                .apply {
                    requestPage?.let{
                        intent = putExtra("deeplink",requestPage)
                    }
                }
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d("onRestart")
    }
}