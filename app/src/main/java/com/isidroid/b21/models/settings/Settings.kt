package com.isidroid.b21.models.settings

import android.content.Context

object Settings {
    private val map = SettingsMap()

    fun init(context: Context) {
        map.init(context)
    }

    var sessionKey: String?
        get() = map.string(SettingId.SESSION_KEY, null)
        set(value) = map.save(SettingId.SESSION_KEY, value)

    var firebaseToken: String?
        get() = map.string(SettingId.FIREBASE_TOKEN, null)
        set(value) = map.save(SettingId.FIREBASE_TOKEN, value)
}