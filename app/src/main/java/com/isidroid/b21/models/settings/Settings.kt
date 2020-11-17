package com.isidroid.b21.models.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import java.util.concurrent.TimeUnit

object Settings {
    private val map = SettingsMap()

    fun init(context: Context) {
        map.init(context)
    }

    var sessionKey: String?
        get() = map.string(SettingId.SESSION_KEY, null)
        set(value) = map.save(SettingId.SESSION_KEY, value)
}