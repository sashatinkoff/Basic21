package com.isidroid.b21.network.apis

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiSession {
    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String): Response<ResponseBody>
}