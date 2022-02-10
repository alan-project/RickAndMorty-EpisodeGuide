package net.alanproject.rickandmorty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.rickandmorty.R
import net.alanproject.rickandmorty.databinding.ItemEpisodeBinding
import timber.log.Timber

class EpisodeAdapter(
    private val items: MutableList<DomainEpiFromAsset> = mutableListOf(),
    private val itemClickListener: (DomainEpiFromAsset) -> Unit
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeHolder {
        Timber.d("onCreateVIewHolder In")
        val binding = DataBindingUtil.inflate<ItemEpisodeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_episode, parent, false
        )
        return EpisodeHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: MutableList<DomainEpiFromAsset>) {
        val startPos = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startPos, newItems.size)
    }

    class EpisodeHolder(
        private val binding: ItemEpisodeBinding,
        private val itemClickListener: (DomainEpiFromAsset) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(domainEpiFromAsset: DomainEpiFromAsset) {
            binding.model = domainEpiFromAsset
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                itemClickListener(domainEpiFromAsset)
            }
        }
    }
}