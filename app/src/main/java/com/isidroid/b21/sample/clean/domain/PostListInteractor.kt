package com.isidroid.b21.sample.clean.domain

import com.isidroid.b21.sample.network.Post
import com.isidroid.b21.sample.clean.model.IPostRepository
import java.util.*
import javax.inject.Inject

class PostListInteractor @Inject constructor(private val repository: IPostRepository) :
    IPostListUseCase {
    override val name = "PostListInteractor_${UUID.randomUUID().toString().substring(0, 5)}"

    override fun get(): List<Post> = repository.list()
}