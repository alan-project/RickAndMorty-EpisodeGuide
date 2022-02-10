package net.alanproject.rickandmorty.ui.activity.location

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.domain.model.DomainLocationModel
import net.alanproject.rickandmorty.R
import net.alanproject.domain.common.KEY_CHAR
import net.alanproject.domain.common.KEY_LOC
import net.alanproject.rickandmorty.databinding.ActivityLocationBinding
import net.alanproject.rickandmorty.ui.activity.character.CharacterActivity
import net.alanproject.rickandmorty.ui.adapter.CharacterAdapter
import timber.log.Timber

@AndroidEntryPoint
class LocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationBinding
    private val viewModel: LocationViewModel by viewModels()

    private val _location: MutableLiveData<DomainLocationModel> = MutableLiveData()
    val location: LiveData<DomainLocationModel>
        get() = _location

    private var locationModel: DomainLocationModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_location)
        binding.vm = viewModel.apply{
            viewModel.characterList.observe(this@LocationActivity) { characterList ->
                Timber.d("initObserving: $characterList")
                if (characterList.isNullOrEmpty()) {
                    Timber.d("initObserving In")
                    binding.tvNoResident.visibility = View.VISIBLE
                }
            }
        }
        binding.view = this
        binding.lifecycleOwner = this

        getLocations()
        getResidents()
        initView()
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
                binding.collapseToolbar.title = _location.value?.name
                binding.collapseToolbar.collapsedTitleGravity = 1
                isShow = true
            } else if (isShow) {
                binding.collapseToolbar.title = " "
                isShow = false
            }
        })
    }

    private fun initAdapter() {
        binding.recyclerCharacters.adapter = CharacterAdapter { domainCharacterModel ->
            val intent = Intent(this, CharacterActivity::class.java)
                .putExtra(KEY_CHAR, domainCharacterModel)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun getResidents() {
        locationModel?.let { viewModel.getResidents(it) }
    }

    private fun getLocations() {
        Timber.d("getLocations In")

        locationModel = intent.getParcelableExtra(KEY_LOC)
        _location.value = locationModel

    }
}