package com.isidroid.b21.mvp

import com.isidroid.b21.di.NetworkModule
import com.isidroid.b21.sample.clean.presentation.MainPresenter
import com.isidroid.b21.sample.di.SdkScope
import com.isidroid.b21.sample.di.SampleModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@SdkScope @Component(modules = [NetworkModule::class, SampleModule::class])
interface PresenterInjector {
    fun inject(presenter: MainPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        @BindsInstance fun view(view: IBaseView): Builder
    }
}