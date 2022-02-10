package net.alanproject.rickandmorty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.alanproject.domain.model.DomainLocationModel
import net.alanproject.rickandmorty.R
import net.alanproject.rickandmorty.databinding.ItemLocationBinding

class LocationsAdapter(
    private val items: MutableList<DomainLocationModel> = mutableListOf(),
    private val itemClickListener: (DomainLocationModel) -> Unit
) : RecyclerView.Adapter<LocationsAdapter.LocationHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationHolder {
        val binding = DataBindingUtil.inflate<ItemLocationBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_location, parent, false
        )
        return LocationHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: MutableList<DomainLocationModel>) {
        val startPos = items.size
//        Timber.d("startPos: $startPos, newItems: $newItems")
        items.addAll(newItems)
        notifyItemRangeInserted(startPos, newItems.size)
    }

    fun submit(newItems: MutableList<DomainLocationModel>?) {

        items.clear()
        if (!newItems.isNullOrEmpty()) items.addAll(newItems)
        notifyDataSetChanged()
    }

    class LocationHolder(
        private val binding: ItemLocationBinding,
        private val itemClickListener: (DomainLocationModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(location: DomainLocationModel) {
            binding.model = location
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                itemClickListener(location)
            }
        }
    }
}