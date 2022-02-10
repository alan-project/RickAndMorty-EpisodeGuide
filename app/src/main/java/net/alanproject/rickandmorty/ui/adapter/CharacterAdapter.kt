package net.alanproject.rickandmorty.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.rickandmorty.R
import net.alanproject.domain.common.GROUP_VIEW
import net.alanproject.domain.common.LIST_VIEW
import net.alanproject.rickandmorty.databinding.ItemCharacterBinding
import net.alanproject.rickandmorty.databinding.ItemCharacterGridBinding
import timber.log.Timber

class CharacterAdapter(
    private val items: MutableList<DomainCharacterModel> = mutableListOf(),
    private val itemClickListener: (DomainCharacterModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var curViewType: Int = LIST_VIEW

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (curViewType == LIST_VIEW) {
            val binding = DataBindingUtil.inflate<ItemCharacterBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_character, parent, false
            )
            return CharacterListHolder(binding, itemClickListener)

        } else if (curViewType == GROUP_VIEW) {
            val binding = DataBindingUtil.inflate<ItemCharacterGridBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_character_grid, parent, false
            )
            return CharacterGroupHolder(binding, itemClickListener)
        }

        val binding = DataBindingUtil.inflate<ItemCharacterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_character, parent, false
        )

        return CharacterListHolder(binding, itemClickListener)
    }


    fun setItemViewType(type: Int) {
        curViewType = type

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        holder.bind(items[position])

        when (curViewType) {
            LIST_VIEW -> {
                (holder as CharacterListHolder).bind(items[position])
                holder.setIsRecyclable(false)
            }
            GROUP_VIEW -> {

                (holder as CharacterGroupHolder).bind(items[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as CharacterListHolder).bind(items[position])
                holder.setIsRecyclable(false)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: MutableList<DomainCharacterModel>) {
        Timber.d("newItems: " + newItems.size)
        val startPos = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startPos, newItems.size)
    }

    fun submit(newItems: MutableList<DomainCharacterModel>?) {

        items.clear()
        if (!newItems.isNullOrEmpty()) items.addAll(newItems)
        notifyDataSetChanged()
    }

    class CharacterListHolder(
        private val binding: ItemCharacterBinding,
        private val itemClickListener: (DomainCharacterModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(domainCharacterModel: DomainCharacterModel) {
//            Timber.d("EpisodeHolder In")
            binding.character = domainCharacterModel
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                itemClickListener(domainCharacterModel)
            }
        }
    }

    class CharacterGroupHolder(
        private val binding: ItemCharacterGridBinding,
        private val itemClickListener: (DomainCharacterModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(domainCharacterModel: DomainCharacterModel) {
//            Timber.d("EpisodeHolder In")
            binding.character = domainCharacterModel
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                itemClickListener(domainCharacterModel)
            }
        }
    }


}
