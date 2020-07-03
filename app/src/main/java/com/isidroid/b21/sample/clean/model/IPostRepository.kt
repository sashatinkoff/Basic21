package com.isidroid.b21.sample.clean.model

import com.isidroid.b21.sample.clean.network.Post

interface IPostRepository {
    fun list(): List<Post>

}