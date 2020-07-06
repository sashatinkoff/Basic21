package com.isidroid.b21

import android.app.Application
import android.content.Context
import com.isidroid.b21.di.DaggerAppComponent
import com.isidroid.b21.sample.di.YRealm
import com.yandex.mapkit.MapKitFactory
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class App : Application() {
    internal val appComponent by lazy { DaggerAppComponent.factory().create(this) }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        YRealm.init(this)
        MapKitFactory.setApiKey("a8b02d46-c3ae-4d30-8c27-86ba6c3e71bc");
    }
}

