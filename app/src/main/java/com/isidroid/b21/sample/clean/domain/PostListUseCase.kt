package com.isidroid.b21.sample.clean.domain

import com.isidroid.b21.sample.Post

interface PostListUseCase {
    fun get(): List<Post>
}