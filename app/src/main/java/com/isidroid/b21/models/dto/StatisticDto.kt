package com.isidroid.b21.models.dto

import java.math.BigDecimal
import kotlin.math.roundToInt

data class StatisticDto(
    val name: String,
    val count: Int,
    val probability: Float,
    val total: Int
) {
    val percentage: String
        get() {
            val rounded = (probability * 100.0).roundToInt() / 100.0
            return "${(rounded * 100).roundToInt()}%"
        }
}