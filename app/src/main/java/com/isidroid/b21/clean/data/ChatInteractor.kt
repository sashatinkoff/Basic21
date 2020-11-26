package com.isidroid.b21.clean.data

import com.isidroid.b21.clean.domain.IChatUseCase
import com.isidroid.b21.models.db.Account
import com.isidroid.b21.network.ApiChat
import com.isidroid.b21.network.ChatReq
import java.lang.Exception

class ChatInteractor(private val api: ApiChat) : IChatUseCase {
    private val serverKey =
        "AAAAWpJBgk0:APA91bFZlIqbsDfLAJnOEfTWnmvVGfFmckSf4mnuRmAgu53GTkehoXsuUsb0rJ6oY9mDppcwB6B7Gos1HUH3FgbKoQVPo9mLZZVDc4uQIkmKAYvSneobEP_fvgiI0WlqTMkNAlwKbmvx"

    override fun sendMessage(account: Account, message: String) {
        val response = api.send(
            authorization = "key=$serverKey",
            body = ChatReq.create(account, message)
        ).execute()

        if (!response.isSuccessful) throw Exception(response.errorBody()?.string())
    }
}