package net.alanproject.rickandmorty.ui.activity.character

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.rickandmorty.R
import net.alanproject.domain.common.KEY_CHAR
import net.alanproject.domain.common.KEY_EPI
import net.alanproject.rickandmorty.databinding.ActivityCharacterBinding
import net.alanproject.rickandmorty.ui.activity.episode.EpisodeActivity
import net.alanproject.rickandmorty.ui.adapter.EpisodeAdapter

@AndroidEntryPoint
class CharacterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterBinding
    private val viewModel: CharacterViewModel by viewModels()

    private val _character: MutableLiveData<DomainCharacterModel> =
        MutableLiveData(DomainCharacterModel())
    val character: LiveData<DomainCharacterModel>
        get() = _character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character)
        binding.vm = viewModel
        binding.view = this
        binding.lifecycleOwner = this

        initView()
        getData()
        initAdapter()
    }

    private fun initView() {

        var isShow = true
        var scrollRange = -1
        binding.appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                binding.collapseToolbar.title = _character.value?.name
                binding.collapseToolbar.collapsedTitleGravity = 1
                isShow = true
            } else if (isShow) {
                binding.collapseToolbar.title = " "
                isShow = false
            }
        })
    }

    private fun getData() {

        val character = intent.getParcelableExtra<DomainCharacterModel>(KEY_CHAR)
        _character.value = character

        character?.let {
            viewModel.getEpisode(it.episode)
        }
    }

    private fun initAdapter() {
        binding.recyclerEpisode.adapter = EpisodeAdapter { domainEpiFromAsset ->
            val epId: Long = domainEpiFromAsset.no
            val intent = Intent(this, EpisodeActivity::class.java)
                .putExtra(KEY_EPI, epId)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

}