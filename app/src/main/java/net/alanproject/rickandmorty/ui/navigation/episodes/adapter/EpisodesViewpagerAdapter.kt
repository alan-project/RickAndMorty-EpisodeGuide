package net.alanproject.rickandmorty.ui.navigation.episodes.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import net.alanproject.domain.common.KEY_SEASON
import net.alanproject.domain.common.KEY_SEASON_NO
import net.alanproject.rickandmorty.ui.navigation.episodes.EpisodeListFragment


class EpisodesViewpagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = KEY_SEASON_NO

    override fun createFragment(position: Int): Fragment {
        return EpisodeListFragment().apply {
            arguments = bundleOf(KEY_SEASON to "${position + 1}")
        }
    }
}
