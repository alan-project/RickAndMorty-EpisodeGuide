package net.alanproject.rickandmorty.ui.navigation.locations

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.rickandmorty.R
import net.alanproject.domain.common.KEY_LOC
import net.alanproject.rickandmorty.databinding.FragmentLocationsBinding
import net.alanproject.rickandmorty.ui.LinearRecyclerViewScrollListener
import net.alanproject.rickandmorty.ui.activity.location.LocationActivity
import net.alanproject.rickandmorty.ui.activity.search.SearchLocationActivity
import net.alanproject.rickandmorty.ui.adapter.LocationsAdapter
import timber.log.Timber


@AndroidEntryPoint
class LocationsFragment : Fragment() {

    private lateinit var binding: FragmentLocationsBinding
    private lateinit var viewModel: LocationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_locations, container, false)
        viewModel = ViewModelProvider(this)[LocationsViewModel::class.java]
        binding.vm = viewModel
        binding.activity = this
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("onViewCreated IN")
        viewModel.getAllLocations()

        initAdapter()
    }

    private fun initAdapter() {
        Timber.d("initAdapter In")

        binding.recyclerviewLocations.adapter = LocationsAdapter { Location ->

            val intent = Intent(context, LocationActivity::class.java)
                .putExtra(KEY_LOC, Location)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        binding.recyclerviewLocations.addOnScrollListener(scrollListener)
    }

    private val scrollListener: RecyclerView.OnScrollListener by lazy {
        LinearRecyclerViewScrollListener(callback = viewModel::getAllLocations)
    }

    fun search(view: View) {
        val intent = Intent(activity, SearchLocationActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

}