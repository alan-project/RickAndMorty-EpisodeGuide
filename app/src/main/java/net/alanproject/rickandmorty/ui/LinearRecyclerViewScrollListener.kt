package net.alanproject.rickandmorty.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinearRecyclerViewScrollListener(
    private val triggerCount: Int = 2,
    private val callback: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        (recyclerView.layoutManager as LinearLayoutManager?)?.let {
            val position = it.findLastCompletelyVisibleItemPosition()
            recyclerView.adapter?.let { adapter ->
                val remainCount = adapter.itemCount - position
                if (remainCount < triggerCount) {
                    callback()
                }
            }
        }
    }
}