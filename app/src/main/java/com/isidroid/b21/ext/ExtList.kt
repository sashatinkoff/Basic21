package com.isidroid.b21.ext

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isidroid.b21.utils.views.adapters.CoreBindAdapter

fun RecyclerView.scroll(adapter: CoreBindAdapter<*>) {
    val scrollListener = object : RecyclerView.OnScrollListener() {
        private var currentItem = -1
        private var firstVisibleItem = 0
        private var visibleItemCount = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val manager = recyclerView.layoutManager as LinearLayoutManager
            visibleItemCount = manager.childCount
            firstVisibleItem = manager.findFirstCompletelyVisibleItemPosition()

            if (firstVisibleItem != currentItem) {
                currentItem = firstVisibleItem
                onItemIsFirstVisibleItem(firstVisibleItem)
            }
        }

        private fun onItemIsFirstVisibleItem(firstVisibleItem: Int) {
            post { adapter.onActive(firstVisibleItem) }
        }
    }

    addOnScrollListener(scrollListener)
    this.adapter = adapter
}