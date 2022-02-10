package net.alanproject.rickandmorty.ui.navigation.quote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.alanproject.rickandmorty.R
import net.alanproject.domain.model.DomainQuoteModel

class QuotesPageAdapter(private val domainQuoteModels: List<DomainQuoteModel>) :
    RecyclerView.Adapter<QuotesPageAdapter.QuoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QuoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        )

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val actualPosition = position % domainQuoteModels.size
        holder.bind(domainQuoteModels[actualPosition])
    }

    override fun getItemCount()=Int.MAX_VALUE

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val quoteTextView: TextView = itemView.findViewById(R.id.txt_quote)
        private val nameTextView: TextView = itemView.findViewById(R.id.txt_name)

        fun bind(domainQuoteModel: DomainQuoteModel) {
            quoteTextView.text = "\"${domainQuoteModel.quote}\""
            nameTextView.text = "-${domainQuoteModel.name}"
        }
    }
}