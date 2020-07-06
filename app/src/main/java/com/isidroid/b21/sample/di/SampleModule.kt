package com.isidroid.b21.sample.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.isidroid.b21.sample.clean.domain.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SampleModule {
    @Singleton @Provides
    fun provideLocationClient(context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Singleton @Provides
    fun provideLocationUseCase(client: FusedLocationProviderClient): LocationUseCase =
        LocationInteractor(client)

    @Singleton @Provides
    fun provideDbLocationUseCase(): DbLocationUseCase = DbLocationInteractor()
}