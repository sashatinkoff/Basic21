package com.isidroid.b21.clean.data

import android.content.Context
import com.isidroid.b21.clean.domain.LotteryRepository
import com.isidroid.b21.clean.domain.LotteryUseCase
import com.isidroid.b21.clean.repository.LotteryRepositoryImpl
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

        repository.parseLines(lines).forEach { pair ->
            pair.first.forEach { repository.collectSingle(it, results.first) }
            pair.second.forEach { repository.collectSingle(it, results.second) }
        }

        return Pair(
            results.first.sortedByDescending { it.counter },
            results.second.sortedByDescending { it.counter }
        )
    }
}