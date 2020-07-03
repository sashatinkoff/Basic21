package com.isidroid.b21.sample

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("posts")
    fun list(): Call<List<Post>>
}

data class Post(@SerializedName("id") var id: Int)