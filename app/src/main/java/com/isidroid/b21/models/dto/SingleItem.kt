package com.isidroid.b21.models.dto

data class SingleItem(val number: Int) : Item {
    override var counter: Int = 0

    override fun statistic(total: Int) =
        StatisticDto(name = "$number", count = counter, probability = probability(total))
}