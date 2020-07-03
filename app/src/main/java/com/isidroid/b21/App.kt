package com.isidroid.b21

import android.app.Application
import android.content.Context
import com.isidroid.b21.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {
    internal val appComponent by lazy { DaggerAppComponent.factory().create(this) }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}

