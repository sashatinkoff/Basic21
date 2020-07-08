package com.isidroid.b21.sample.di

import com.isidroid.b21.sample.network.Api
import com.isidroid.b21.sample.clean.domain.PostListInteractor
import com.isidroid.b21.sample.clean.domain.IPostListUseCase
import com.isidroid.b21.sample.clean.model.IPostRepository
import com.isidroid.b21.sample.clean.model.PostRepository
import dagger.Module
import dagger.Provides
import timber.log.Timber
import java.util.*
import javax.inject.Singleton

@Module
class SampleModule {
    init {
        Timber.e("PostListIn SampleModule.init")
    }

    @Singleton @Provides
    fun providePostRepository(api: Api): IPostRepository = PostRepository(api)

    @Singleton @Provides
    fun providePostLiseUseCase(api: Api): IPostListUseCase =
        PostListInteractor(providePostRepository(api))
}