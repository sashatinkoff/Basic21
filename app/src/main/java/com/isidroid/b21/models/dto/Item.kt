package com.isidroid.b21.models.dto

interface Item {
    var counter: Int
    fun probability(total: Int): Float = counter.toFloat() / total.toFloat()
    fun statistic(total: Int): StatisticDto
}