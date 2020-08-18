package com.isidroid.b21.network

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiFirebaseMessaging {
    @POST("send")
    fun send(
        @Header("Authorization") authorization: String,
        @Body body: MessageRequest
    ): Call<ResponseBody>

    data class MessageRequest(
        @SerializedName("topic") val topic: String? = null,
        @SerializedName("to") val to: String? = null,
        @SerializedName("notification") val notification: NotificationRequest? = null
    )

    data class NotificationRequest(
        @SerializedName("body") val body: String? = null,
        @SerializedName("title") val title: String? = null
    )
}