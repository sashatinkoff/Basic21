package com.isidroid.b21.models.dto

interface Item {
    var counter: Int
    var total: Int

    val probability
        get() = counter.toFloat() / total.toFloat()

    val statistic: StatisticDto
}