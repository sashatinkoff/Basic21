package com.isidroid.b21.di

import com.isidroid.b21.clean.data.FirebaseMessagingInteractor
import com.isidroid.b21.clean.domain.IFirebaseMessagingUseCase
import com.isidroid.b21.network.ApiFirebaseMessaging
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SampleModule {
    @JvmStatic @Provides @Singleton
    fun provideFirebaseMessagingUseCase(
        api: ApiFirebaseMessaging,
        @FirebaseRegistrationTokenId token: String
    ): IFirebaseMessagingUseCase = FirebaseMessagingInteractor(api = api, registrationToken = token)

}