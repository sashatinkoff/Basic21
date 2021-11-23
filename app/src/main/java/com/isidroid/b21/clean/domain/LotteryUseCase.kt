package com.isidroid.b21.clean.domain

import com.isidroid.b21.models.dto.DoubleItem
import com.isidroid.b21.models.dto.SingleItem

interface LotteryUseCase {
    fun singleStatistics(fileName: String): Pair<List<SingleItem>, List<SingleItem>>
    fun doubleStatistics(fileName: String): Pair<List<DoubleItem>, List<DoubleItem>>
}