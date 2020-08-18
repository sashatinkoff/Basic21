package com.isidroid.b21

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.isidroid.b21.di.DaggerAppComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmMigration
import timber.log.Timber

class App : Application() {
    internal val appComponent by lazy { DaggerAppComponent.factory().create(this) }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        configureRealm()

        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }

    private fun configureRealm() {
        Realm.init(this)
        val migration: RealmMigration? = null
        val builder = RealmConfiguration.Builder()
            .name("database.realm")
            .schemaVersion(1L)

        migration?.let { builder.migration(it) } ?: builder.deleteRealmIfMigrationNeeded()
        builder.build().apply { Realm.setDefaultConfiguration(this) }
    }
}

