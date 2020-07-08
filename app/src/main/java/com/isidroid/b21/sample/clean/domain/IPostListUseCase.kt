package com.isidroid.b21.sample.clean.domain

import com.isidroid.b21.sample.network.Post

interface IPostListUseCase {
    val name: String
    fun get(): List<Post>
}