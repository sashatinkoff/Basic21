package com.isidroid.b21.models.dto

data class DoubleItem(val number1: Int, val number2: Int, override var total: Int) : Item {
    override var counter: Int = 0
    override val statistic: StatisticDto
        get() = StatisticDto(
            name = "$number1:$number2",
            count = counter,
            probability = probability,
            total = total
        )


}