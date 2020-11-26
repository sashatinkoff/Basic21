package com.isidroid.b21

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.isidroid.b21.di.DaggerAppComponent
import com.isidroid.b21.models.settings.Settings
import com.isidroid.b21.utils.NotificationsChannels
import com.isidroid.b21.utils.YDebugTree
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    internal val appComponent by lazy { DaggerAppComponent.factory().create(this) }

    override fun onCreate() {
        super.onCreate()
        instance = this

        FirebaseApp.initializeApp(applicationContext)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

        Timber.plant(YDebugTree())
        Settings.init(this)
        NotificationsChannels(this)

        createRealm()
    }

    private fun createRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("db_reddit.realm")
            .schemaVersion(1L)
            .deleteRealmIfMigrationNeeded()

        // config.migration(migration)
        Realm.setDefaultConfiguration(config.build())
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }
}

val Context.appComponent
    get() = (applicationContext as App).appComponent
val Fragment.appComponent
    get() = requireActivity().appComponent