package com.isidroid.b21.network

import com.isidroid.b21.clean.domain.ISessionUseCase
import okhttp3.*


private const val AUTHORIZATION = "Authorization"

class AuthInterceptor(private val sessionUseCase: ISessionUseCase) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        sessionUseCase.refreshToken()

        return response.request.newBuilder()
            .removeHeader(AUTHORIZATION)
            .addHeader(AUTHORIZATION, "Bearer ${sessionUseCase.accessToken}")
            .build()
    }
}