package net.alanproject.rickandmorty.ui.navigation.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.rickandmorty.R
import net.alanproject.rickandmorty.databinding.FragmentEpisodesBinding
import net.alanproject.rickandmorty.ui.navigation.episodes.adapter.EpisodesViewpagerAdapter

@AndroidEntryPoint
class EpisodesFragment : Fragment() {

    private lateinit var binding: FragmentEpisodesBinding
    private lateinit var viewModel: EpisodesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_episodes, container, false)
        viewModel = ViewModelProvider(this)[EpisodesViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        val adapter = EpisodesViewpagerAdapter(requireActivity())
        binding.viewpagerEpiList.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewpagerEpiList) { tab, position ->
        }.attach()

        val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> updateImage(R.drawable.season01)
                    1 -> updateImage(R.drawable.season05)
                    2 -> updateImage(R.drawable.season03)
                    3 -> updateImage(R.drawable.season04)
                    4 -> updateImage(R.drawable.season02)
                }
                super.onPageSelected(position)
            }
        }

        binding.viewpagerEpiList.registerOnPageChangeCallback(viewPagerCallback)


    }

    private fun updateImage(resource: Int) {
        Glide.with(requireActivity())
            .load(resource)
            .transform(CenterCrop(), RoundedCorners(52))
            .into(binding.ivSeason)
    }

}