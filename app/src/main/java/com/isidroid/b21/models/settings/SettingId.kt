package com.isidroid.b21.models.settings

enum class SettingId(val type: SettingType, val title: Int? = null, val description: Int? = null) {
    // string
    THEME(type = SettingType.INT),
    SESSION_KEY(type = SettingType.STRING);

    fun update(value: Boolean) {
        when (this) {

        }
    }

    fun update(value: Int) {
        when (this) {
            THEME -> Settings.theme = value
        }
    }
}

enum class SettingType { BOOL, STRING, INT, LONG }