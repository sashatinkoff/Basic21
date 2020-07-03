package com.isidroid.b21.di

import com.isidroid.b21.sample.clean.network.Api
import com.isidroid.b21.sample.clean.domain.PostListInteractor
import com.isidroid.b21.sample.clean.domain.PostListUseCase
import com.isidroid.b21.sample.clean.model.IPostRepository
import com.isidroid.b21.sample.clean.model.PostRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SampleModule {
    @Singleton @Provides
    fun providePostRepository(api: Api): IPostRepository = PostRepository(api)

    @Singleton @Provides
    fun providePostLiseUseCase(api: Api): PostListUseCase =
        PostListInteractor(providePostRepository(api))
}