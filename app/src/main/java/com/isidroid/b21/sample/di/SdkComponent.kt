package com.isidroid.b21.sample.di

import com.isidroid.b21.di.AppComponent
import com.isidroid.b21.di.NetworkModule
import com.isidroid.b21.sample.SquareOffSdk
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@SdkScope
@Subcomponent(
//    modules = [NetworkModule::class, SampleModule::class]
)
interface SdkComponent {
    fun inject(sdk: SquareOffSdk)

    @Subcomponent.Factory
    interface Factory {
        fun create(): SdkComponent
    }
}