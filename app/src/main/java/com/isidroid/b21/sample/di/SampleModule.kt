package com.isidroid.b21.sample.di

import com.isidroid.b21.sample.clean.domain.IPostListUseCase
import com.isidroid.b21.sample.clean.domain.PostListInteractor
import com.isidroid.b21.sample.clean.model.IPostRepository
import com.isidroid.b21.sample.clean.model.PostRepository
import com.isidroid.b21.sample.network.Api
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class SampleModule {
    @Provides @Singleton
    fun providePostList(repository: IPostRepository): IPostListUseCase =
        PostListInteractor(repository)

    @Provides
    fun providePostRepository(api: Api): IPostRepository = PostRepository(api)
}