package com.isidroid.b21.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiChat {
    @POST("send")
    fun send(
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Body body: ChatReq
    ): Call<ResponseBody>
}