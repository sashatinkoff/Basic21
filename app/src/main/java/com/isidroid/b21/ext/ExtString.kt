package com.isidroid.b21.ext

import android.text.Spanned
import android.text.format.DateUtils
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import java.security.MessageDigest
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow

fun Long.withSuffix(): String {
    if (this < 1000) return "" + this
    val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
    return String.format(
        "%.1f%c",
        this / 1000.0.pow(exp.toDouble()),
        "kMGTPE"[exp - 1]
    )
}

fun Int.withSuffix() = this.toLong().withSuffix()

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digested = md.digest(toByteArray())
    return digested.joinToString("") { String.format("%02x", it) }
}

fun String.clearUrl() = with(toUri()) {
    toString()
        .replace(query.orEmpty(), "")
        .replace("^/+".toRegex(), "")
}

fun String.toFileName() = take(20)
    .let { "\\s".toRegex().replace(it, "_") }
    .let { "[^a-zA-Z0-9 ]".toRegex().replace(it, "_") }
    .let { "_{2,}".toRegex().replace(it, "") }
    .toLowerCase()

fun Long.toFileSize(): String {
    if (this <= 0) return "0";
    val size = toDouble()

    val units = listOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = log10(size) / log10(1024.0)

    return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups)) + " " + units[digitGroups.toInt()]
}

fun String.findTags(tag: String): List<Pair<String, String>> {
    val result = mutableListOf<Pair<String, String>>()

    val regex = "(?i)<$tag[^>]+>([^<]+)<\\/$tag>"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    while (matcher.find()) {
        result.add(Pair(matcher.group(0), matcher.group(1)))
    }
    return result.filter { it.first.isNotEmpty() && it.second.isNotEmpty() }
}