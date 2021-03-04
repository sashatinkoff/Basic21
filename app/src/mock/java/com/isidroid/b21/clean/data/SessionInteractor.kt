package com.isidroid.b21.clean.data

import com.isidroid.b21.clean.domain.ISessionUseCase
import com.isidroid.b21.network.apis.ApiSession

class SessionInteractor(api: ApiSession) : ISessionUseCase {
    override var accessToken: String? = null
    override var refreshToken: String? = null
    override fun refreshToken() {}
}