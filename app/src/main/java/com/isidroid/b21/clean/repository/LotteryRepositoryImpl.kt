package com.isidroid.b21.clean.repository

import android.content.Context
import com.isidroid.b21.clean.domain.LotteryRepository
import com.isidroid.b21.models.dto.DoubleItem
import com.isidroid.b21.models.dto.SingleItem
import timber.log.Timber

class LotteryRepositoryImpl : LotteryRepository {
    override fun readAssets(context: Context, fileName: String): String =
        context.assets.open(fileName).bufferedReader().use { it.readText() }

    override fun parseLines(lines: List<String>) = lines.map { line ->
        val numbers = line.split(" ").map { it.toInt() }
        val first = numbers.take(4)
        val last = numbers.takeLast(4)
        Pair(first, last)
    }

    override fun collectSingle(number: Int, data: MutableList<SingleItem>, total: Int) {
        if (!data.any { it.number == number }) data.add(SingleItem(number, total))

        val item = data.first { it.number == number }
        item.counter++
    }

    override fun collectDouble(collection: List<Int>, data: MutableList<DoubleItem>, total: Int) {
        val combinations = combinations(collection)
        combinations.forEach { collectCombination(it, data, total) }
    }

    override fun collectCombination(
        values: Pair<Int, Int>,
        data: MutableList<DoubleItem>,
        total: Int
    ) {
        if (!data.any { it.number1 == values.first && it.number2 == values.second })
            data.add(DoubleItem(values.first, values.second, total))

        val item = data.first { it.number1 == values.first && it.number2 == values.second }
        item.counter++
    }

    override fun combinations(collection: List<Int>): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()

        for (i in collection.indices) {
            val first = collection[i]
            var k = i + 1
            while (k < collection.size) {
                val second = collection[k]
                result.add(Pair(first, second))
                k++
            }
        }

        return result
    }


}