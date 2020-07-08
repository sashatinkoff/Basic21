package com.isidroid.b21.sample.clean.domain

import com.isidroid.b21.sample.network.Post
import com.isidroid.b21.sample.clean.model.IPostRepository
import javax.inject.Inject

class PostListInteractor @Inject constructor(private val repository: IPostRepository) : PostListUseCase {
    override fun get(): List<Post> = repository.list()
}