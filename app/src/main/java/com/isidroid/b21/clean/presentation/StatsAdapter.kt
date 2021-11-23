package com.isidroid.b21.clean.presentation

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.isidroid.b21.R
import com.isidroid.b21.databinding.ItemHeaderBinding
import com.isidroid.b21.databinding.ItemStatisticBinding
import com.isidroid.b21.models.dto.StatisticDto
import com.isidroid.b21.utils.views.adapters.CoreBindAdapter
import java.lang.IllegalStateException

private const val TYPE_STATISTIC = 101
private const val TYPE_HEADER = 102

class StatsAdapter : CoreBindAdapter<Any>() {

    override fun getItemViewType(position: Int) = when (items.getOrNull(position)) {
        is StatisticDto -> TYPE_STATISTIC
        is String -> TYPE_HEADER
        else -> super.getItemViewType(position)
    }

    override fun resource(viewType: Int): Int = when (viewType) {
        TYPE_STATISTIC -> R.layout.item_statistic
        TYPE_HEADER -> R.layout.item_header
        else -> throw IllegalStateException()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindHolder(binding: ViewDataBinding, position: Int) {
        (binding as? ItemHeaderBinding)?.apply { textView.text = "${items[position]}" }
        (binding as? ItemStatisticBinding)?.apply {
            val statistics = items.getOrNull(position) as? StatisticDto ?: return@apply

            nameView.text = statistics.name
            textView.text = "${statistics.count}/${statistics.total} (${statistics.percentage})"

            root.setOnClickListener { }
        }
    }
}