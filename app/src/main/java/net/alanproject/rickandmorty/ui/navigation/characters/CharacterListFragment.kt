package net.alanproject.rickandmorty.ui.navigation.characters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.rickandmorty.R
import net.alanproject.domain.common.KEY_CHAR
import net.alanproject.rickandmorty.databinding.FragmentCharacterListBinding
import net.alanproject.rickandmorty.ui.LinearRecyclerViewScrollListener
import net.alanproject.rickandmorty.ui.activity.character.CharacterActivity
import net.alanproject.rickandmorty.ui.activity.search.SearchCharacterActivity
import net.alanproject.rickandmorty.ui.adapter.CharacterAdapter
import timber.log.Timber

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding
    private lateinit var viewModel: CharacterListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_character_list, container, false)
        viewModel = ViewModelProvider(this)[CharacterListViewModel::class.java]
        binding.vm = viewModel
        binding.activity = this@CharacterListFragment
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getAllCharacters()

        initAdapter()

        return binding.root
    }

    private fun initAdapter() {
        Timber.d("initAdapter In")

        binding.recyclerList.adapter = CharacterAdapter { domainCharacterModel ->
            val intent = Intent(activity, CharacterActivity::class.java)
                .putExtra(KEY_CHAR, domainCharacterModel)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        var isGridClicked = false
        binding.ibToggle.setOnClickListener {
            Timber.d("ibToggle is clicked")
            when (isGridClicked) {
                true -> {
                    isGridClicked = false
                    binding.ibToggle.setImageResource(R.drawable.ic_grid)
                    (binding.recyclerList.adapter as CharacterAdapter).setItemViewType(0)
                    binding.recyclerList.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
                false -> {
                    isGridClicked = true
                    binding.ibToggle.setImageResource(R.drawable.ic_list)
                    (binding.recyclerList.adapter as CharacterAdapter).setItemViewType(1)
                    binding.recyclerList.layoutManager =
                        GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                }

            }
        }
/*
        binding.ivList.setOnClickListener {
            Timber.d("ivList is clicked")
            (binding.recyclerList.adapter as CharacterAdapter).setItemViewType(1)
            binding.recyclerList.layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL, false)
        }
    binding.ivGroup.setOnClickListener {
            Timber.d("ivList is clicked")
            (binding.recyclerList.adapter as CharacterAdapter).setItemViewType(0)
            binding.recyclerList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        }*/

        binding.recyclerList.addOnScrollListener(scrollListener)
    }

    fun search(view:View) {
        val intent = Intent(activity, SearchCharacterActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

    private val scrollListener: RecyclerView.OnScrollListener by lazy {
        LinearRecyclerViewScrollListener(callback = viewModel::getAllCharacters)
    }


}