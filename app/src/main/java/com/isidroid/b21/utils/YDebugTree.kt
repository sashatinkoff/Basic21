package com.isidroid.b21.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class YDebugTree : Timber.DebugTree() {
    enum class Key {
        PRIORITY, TAG, MESSAGE
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)

        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)
            return

        val exception = t?.let { Exception(it) } ?: Exception(message)
        FirebaseCrashlytics.getInstance().apply {
            setCustomKey(Key.PRIORITY.name, priority)
            setCustomKey(Key.MESSAGE.name, message)
            tag?.let { setCustomKey(Key.TAG.name, tag) }
            recordException(exception)
        }
    }
}