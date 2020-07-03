package com.isidroid.b21.sample.clean.domain

import com.isidroid.b21.sample.clean.network.Post

interface PostListUseCase {
    fun get(): List<Post>
}