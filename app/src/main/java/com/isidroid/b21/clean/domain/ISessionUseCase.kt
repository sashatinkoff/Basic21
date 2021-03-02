package com.isidroid.b21.clean.domain

interface ISessionUseCase {
    var accessToken: String?
    var refreshToken: String?

    fun refreshToken()
}