package com.isidroid.b21.sample.di

import com.isidroid.b21.sample.network.Api
import com.isidroid.b21.sample.clean.domain.PostListInteractor
import com.isidroid.b21.sample.clean.domain.PostListUseCase
import com.isidroid.b21.sample.clean.model.IPostRepository
import com.isidroid.b21.sample.clean.model.PostRepository
import dagger.Module
import dagger.Provides
import timber.log.Timber
import java.util.*
import javax.inject.Singleton

@Module
class SampleModule {
    @Singleton @Provides
    fun providePostRepository(api: Api): IPostRepository = PostRepository(api)

    @Singleton @Provides
    fun providePostLiseUseCase(api: Api): PostListUseCase =
        PostListInteractor(providePostRepository(api))

    @Provides
    fun provideTest(): String {
        Timber.i("gdfgfgdfgdfgdfg provideTest()")
        return UUID.randomUUID().toString().substring(0, 5)
    }
}