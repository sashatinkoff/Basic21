package com.isidroid.b21.ext

import timber.log.Timber

inline fun <T> catchAll(
    action: () -> T?,
    noinline onFinally: (() -> T?)? = null,
    message: String? = null
): T? {
    return try {
        action()
    } catch (t: Throwable) {
        Timber.i("Failed to $message. ${t.message}")
        null
    } finally {
        onFinally?.invoke()
    }
}