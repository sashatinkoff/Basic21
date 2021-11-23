package com.isidroid.b21.clean.domain

import android.content.Context
import com.isidroid.b21.models.dto.DoubleItem
import com.isidroid.b21.models.dto.SingleItem

interface LotteryRepository {
    fun readAssets(context: Context, fileName: String): String
    fun parseLines(lines: List<String>): List<Pair<List<Int>, List<Int>>>
    fun collectSingle(number: Int, data: MutableList<SingleItem>, total: Int)
    fun collectDouble(collection: List<Int>, data: MutableList<DoubleItem>, total: Int)
    fun collectCombination(values: Pair<Int, Int>, data: MutableList<DoubleItem>, total: Int)
    fun combinations(collection: List<Int>): List<Pair<Int, Int>>
}