package com.isidroid.b21.sample.clean.model

import com.isidroid.b21.sample.Post

interface IPostRepository {
    fun list(): List<Post>

}