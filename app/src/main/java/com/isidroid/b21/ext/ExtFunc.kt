package com.isidroid.b21.ext

fun <T> tryGetResult(
    block: () -> T?,
    onError: ((Throwable) -> T?)? = null
): T? {
    return try {
        block()
    } catch (e: Exception) {
        onError?.let { onError.invoke(e) }
    }
}