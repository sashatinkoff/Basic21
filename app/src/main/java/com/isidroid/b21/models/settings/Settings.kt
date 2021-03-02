package com.isidroid.b21.models.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object Settings {
    private val map = SettingsMap()

    fun init(context: Context) {
        map.init(context)
    }

    var theme: Int
        get() = map.int(SettingId.THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        set(value) = map.save(SettingId.THEME, value)

    var sessionKey: String?
        get() = map.string(SettingId.SESSION_KEY, null)
        set(value) = map.save(SettingId.SESSION_KEY, value)
}