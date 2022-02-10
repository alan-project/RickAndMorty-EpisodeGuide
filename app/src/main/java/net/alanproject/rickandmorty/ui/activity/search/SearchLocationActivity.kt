package net.alanproject.rickandmorty.ui.activity.search

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.rickandmorty.R
import net.alanproject.domain.common.KEY_LOC
import net.alanproject.rickandmorty.databinding.ActivitySearchLocationBinding
import net.alanproject.rickandmorty.ui.LinearRecyclerViewScrollListener
import net.alanproject.rickandmorty.ui.activity.location.LocationActivity
import net.alanproject.rickandmorty.ui.adapter.LocationsAdapter

@AndroidEntryPoint
class SearchLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchLocationBinding
    private val viewModel: SearchLocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_location)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        initListener()
        initAdapter()

    }

    private fun initAdapter() {
        binding.recyclerviewLocations.adapter = LocationsAdapter { Location ->

            val intent = Intent(this, LocationActivity::class.java)
                .putExtra(KEY_LOC, Location)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        binding.recyclerviewLocations.addOnScrollListener(scrollListener)
    }

    private val scrollListener: RecyclerView.OnScrollListener by lazy {
        LinearRecyclerViewScrollListener(callback = viewModel::searchNextPage)
    }

    private fun initListener() {
        binding.etSearch.addTextChangedListener {
            viewModel.search(it.toString())
        }

    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

}