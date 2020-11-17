package com.isidroid.b21.models.settings

enum class SettingId(val type: SettingType, val title: Int? = null, val description: Int? = null) {
    // string
    SESSION_KEY(type = SettingType.STRING);

    fun update(value: Boolean) {
        when (this) {

        }
    }

    fun update(value: Int) {
        when (this) {
            else -> {
            }
        }
    }

    fun update(value: String) {
        when (this) {
            SESSION_KEY -> Settings.sessionKey = value
            else -> {
            }
        }
    }
}

enum class SettingType { BOOL, STRING, INT, LONG }