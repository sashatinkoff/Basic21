package com.isidroid.b21.models.dto

data class SingleItem(val number: Int, override var total: Int) : Item {
    override var counter: Int = 0
    
    override val statistic: StatisticDto
        get() = StatisticDto(
            name = "$number",
            count = counter,
            probability = probability,
            total = total
        )


}