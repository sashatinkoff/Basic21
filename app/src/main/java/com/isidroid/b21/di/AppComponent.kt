package com.isidroid.b21.di

import android.content.Context
import com.isidroid.b21.App
import com.isidroid.b21.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = [NetworkModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
}

fun Context.appComponent() = (applicationContext as App).appComponent