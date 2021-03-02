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

    var accessToken: String?
        get() = map.string(SettingId.ACCESS_TOKEN, null)
        set(value) = map.save(SettingId.ACCESS_TOKEN, value)

    var refreshToken: String?
        get() = map.string(SettingId.REFRESH_TOKEN, null)
        set(value) = map.save(SettingId.REFRESH_TOKEN, value)
}