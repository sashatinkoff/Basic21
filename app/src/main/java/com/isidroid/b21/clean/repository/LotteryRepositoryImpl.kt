package com.isidroid.b21.clean.repository

import android.content.Context
import com.isidroid.b21.clean.domain.LotteryRepository
import com.isidroid.b21.models.dto.SingleItem

class LotteryRepositoryImpl : LotteryRepository {
    override fun readAssets(context: Context, fileName: String): String =
        context.assets.open(fileName).bufferedReader().use { it.readText() }

    override fun parseLines(lines: List<String>) = lines.map { line ->
        val numbers = line.split(" ").map { it.toInt() }
        val first = numbers.take(4)
        val last = numbers.takeLast(4)
        Pair(first, last)
    }

    override fun collectSingle(number: Int, data: MutableList<SingleItem>) {
        if (!data.any { it.number == number }) data.add(SingleItem(number))

        val item = data.first { it.number == number }
        item.counter++
    }


}