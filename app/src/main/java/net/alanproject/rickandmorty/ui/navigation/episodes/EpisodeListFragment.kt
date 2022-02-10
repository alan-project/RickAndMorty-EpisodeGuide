package net.alanproject.rickandmorty.ui.navigation.episodes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.rickandmorty.R
import net.alanproject.domain.common.KEY_EPI
import net.alanproject.domain.common.KEY_SEASON
import net.alanproject.rickandmorty.databinding.FragmentEpisodeListBinding
import net.alanproject.rickandmorty.ui.activity.episode.EpisodeActivity
import net.alanproject.rickandmorty.ui.adapter.EpisodeAdapter
import timber.log.Timber

@AndroidEntryPoint
class EpisodeListFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeListBinding
    private lateinit var viewModel: EpisodeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("onCreateView")
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_episode_list, container, false)
        viewModel = ViewModelProvider(this)[EpisodeListViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("onViewCreated")
        getData()
        initAdapter()

    }

    private fun getData() {

        val season: String = arguments?.getString(KEY_SEASON).orEmpty()
        Timber.d("loadData: $season")
        viewModel.getEpisodes(season)
    }

    private fun initAdapter() {

        binding.recyclerEpisodeList.adapter = EpisodeAdapter { domainEpiFromAsset ->
            val epId: Long = domainEpiFromAsset.no
            val intent = Intent(context, EpisodeActivity::class.java)
                .putExtra(KEY_EPI, epId)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}
