package com.isidroid.b21.sample.di

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*

object YRealm {
    fun init(context: Context) {
        Realm.init(context)
        RealmConfiguration.Builder()
            .schemaVersion(1L)
            .deleteRealmIfMigrationNeeded()
            .build().apply { Realm.setDefaultConfiguration(this) }
    }


    private val poolThread = WeakHashMap<Thread, Realm>()
    val realm: Realm by lazy {
        val thread = Thread.currentThread()
        if (!poolThread.containsKey(thread)) poolThread[thread] = Realm.getDefaultInstance()
        poolThread[thread]!!
    }

}