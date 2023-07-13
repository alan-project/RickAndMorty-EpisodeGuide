package net.alanproject.rickandmorty.ui.activity.episode

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.rickandmorty.R
import net.alanproject.domain.common.KEY_CHAR
import net.alanproject.domain.common.KEY_EPI
import net.alanproject.rickandmorty.databinding.ActivityEpisodeBinding
import net.alanproject.rickandmorty.ui.activity.character.CharacterActivity
import net.alanproject.rickandmorty.ui.adapter.CharacterAdapter
import timber.log.Timber

@AndroidEntryPoint
class EpisodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEpisodeBinding
    private val viewModel: EpisodeViewModel by viewModels()

    private lateinit var episodeModel: DomainEpiFromAsset

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_episode)
        binding.vm = viewModel.apply{
            viewModel.episodeTitleFromAsset.observe(this@EpisodeActivity) { episode ->
                episodeModel = episode
            }
        }
        binding.lifecycleOwner = this



        getEpisode()
        getEpisodeTitle()
        initView()
        initAdapter()
    }

    private fun initView() {

        binding.tvSynop.movementMethod = ScrollingMovementMethod.getInstance()


        //TODO Workaround.
        var isShow = true
        var scrollRange = -1
//        var minValue = Integer.MAX_VALUE
        binding.appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            Timber.d("addOnOffsetChangedListener In")
            Timber.d("scrollRange: $scrollRange, verticalOffset: $verticalOffset, isShow: $isShow")
            if (scrollRange == -1) {
                Timber.d("scrollRange == -1")
                scrollRange = barLayout?.totalScrollRange!!
            }
//            if (scrollRange + verticalOffset == 0 ||scrollRange + verticalOffset == -72 ) {
            if (scrollRange + verticalOffset == 0) {
                Timber.d("scrollRange + verticalOffset == 0 IN")

                binding.collapseToolbar.title = episodeModel.title
                binding.collapseToolbar.collapsedTitleGravity = 1
//                minValue = Math.min(minValue,scrollRange + verticalOffset )
                isShow = true
            } else if (isShow) {
                Timber.d("elseif")
                binding.collapseToolbar.title = " "
                isShow = false
            }
        })
    }

    private fun getEpisode() {
        Timber.d("loadEpisode In")
        intent.getLongExtra(KEY_EPI, 0L).let { epId ->
            viewModel.getEpisode(epId)
        }
    }

    private fun getEpisodeTitle() {
        Timber.d("loadEpisodeTitle In")
        intent.getLongExtra(KEY_EPI, 0L).let { epId ->
            viewModel.getEpisodeTitleFromAsset(epId)
        }

    }

    private fun initAdapter() {
        binding.recyclerCharacters.adapter = CharacterAdapter { domainCharacterModel ->
            val intent = Intent(this, CharacterActivity::class.java)
                .putExtra(KEY_CHAR, domainCharacterModel)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

}