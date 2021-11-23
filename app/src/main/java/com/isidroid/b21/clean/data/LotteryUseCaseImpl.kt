package com.isidroid.b21.clean.data

import android.content.Context
import com.isidroid.b21.clean.domain.LotteryRepository
import com.isidroid.b21.clean.domain.LotteryUseCase
import com.isidroid.b21.models.dto.DoubleItem
import com.isidroid.b21.models.dto.SingleItem
import timber.log.Timber

class LotteryUseCaseImpl(
    private val context: Context,
    private val repository: LotteryRepository
) : LotteryUseCase {

    override fun singleStatistics(fileName: String): Pair<List<SingleItem>, List<SingleItem>> {
        val lines = repository.readAssets(context, fileName).lines()

        val results = Pair(
            mutableListOf<SingleItem>(),
            mutableListOf<SingleItem>()
        )

        val total = lines.size
        repository.parseLines(lines).forEach { pair ->
            pair.first.forEach { repository.collectSingle(it, results.first, total) }
            pair.second.forEach { repository.collectSingle(it, results.second, total) }
        }

        return Pair(
            results.first.sortedByDescending { it.counter },
            results.second.sortedByDescending { it.counter }
        )
    }

    override fun doubleStatistics(fileName: String): Pair<List<DoubleItem>, List<DoubleItem>> {
        val lines = repository.readAssets(context, fileName).lines()
        val total = lines.size

        val results = Pair(
            mutableListOf<DoubleItem>(),
            mutableListOf<DoubleItem>()
        )

        repository.parseLines(lines).forEach { pair ->
            repository.collectDouble(pair.first.sorted(), results.first, total)
            repository.collectDouble(pair.second.sorted(), results.second, total)
        }

        return Pair(
            results.first.sortedByDescending { it.counter },
            results.second.sortedByDescending { it.counter }
        )
    }


}