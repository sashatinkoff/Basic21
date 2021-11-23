package com.isidroid.b21.clean.domain

import com.isidroid.b21.models.dto.SingleItem

interface LotteryUseCase {
    fun singleStatistics(fileName: String): Pair<List<SingleItem>, List<SingleItem>>
}