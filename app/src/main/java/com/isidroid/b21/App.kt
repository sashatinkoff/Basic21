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

    }

}

val Context.appComponent
    get() = (applicationContext as App).appComponent
val Fragment.appComponentx
    get() = requireActivity().appComponent