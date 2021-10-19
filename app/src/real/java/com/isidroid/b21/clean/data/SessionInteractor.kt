package com.isidroid.b21.clean.data

import com.isidroid.b21.clean.domain.ISessionUseCase
import com.isidroid.b21.models.settings.Settings

class SessionInteractor : ISessionUseCase {
    override var accessToken: String? = Settings.accessToken
        set(value) {
            Settings.accessToken = value
            field = value
        }

    override var refreshToken: String? = Settings.refreshToken
        set(value) {
            Settings.refreshToken = value
            field = value
        }


    override fun refreshToken() {

    }
}