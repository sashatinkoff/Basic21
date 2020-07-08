package com.isidroid.b21.sample.di

import com.isidroid.b21.di.AppComponent
import com.isidroid.b21.di.NetworkModule
import com.isidroid.b21.sample.SquareOffSdk
import dagger.Component
import javax.inject.Singleton

@SdkScope
@Component(
    modules = [NetworkModule::class, SampleModule::class]
)
interface SdkComponent {
    fun inject(sdk: SquareOffSdk)

    @Component.Factory
    interface Factory {
        fun create(): SdkComponent
    }
}