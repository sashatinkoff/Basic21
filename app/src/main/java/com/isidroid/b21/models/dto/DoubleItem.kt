package com.isidroid.b21.models.dto

data class DoubleItem(val number1: Int, val number2: Int) : Item {
    override var counter: Int = 0

    override fun statistic(total: Int) =
        StatisticDto(name = "$number1:$number2", count = counter, probability = probability(total))
}