package com.isidroid.b21.sample.clean.model

import com.isidroid.b21.sample.clean.network.Api
import com.isidroid.b21.sample.clean.network.Post
import javax.inject.Inject

class PostRepository @Inject constructor(val api: Api): IPostRepository {
    override fun list(): List<Post> = api.list().execute().body().orEmpty()
}