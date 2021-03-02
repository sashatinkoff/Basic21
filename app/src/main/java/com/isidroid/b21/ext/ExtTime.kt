package com.isidroid.b21.ext

fun Int.greaterHour() = this / 3600 > 0
fun Int.toTimeMinSec() = with(Time(this)) { String.format("%02d:%02d", minutes, seconds) }
fun Int.toTimeHoursMinSec() =
    with(Time(this)) { String.format("%02d:%02d:%02d", hours, minutes, seconds) }


class Time(seconds: Int) {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val seconds = seconds % 60
}