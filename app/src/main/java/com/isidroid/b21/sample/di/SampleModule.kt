package com.isidroid.b21.sample.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.isidroid.b21.sample.clean.domain.LocationInteractor
import com.isidroid.b21.sample.clean.domain.LocationUseCase
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

    @Singleton @Provides
    fun provideLocationClient(context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Singleton @Provides
    fun provideLocationUseCase(client: FusedLocationProviderClient): LocationUseCase = LocationInteractor(client)
}