package com.isidroid.b21.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.isidroid.b21.clean.data.BillingInteractor
import com.isidroid.b21.clean.domain.IBillingUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @JvmStatic @Singleton @Provides
    fun provideBilling(context: Context, prefs: SharedPreferences): IBillingUseCase =
        BillingInteractor(prefs).create(context)

    @JvmStatic @Singleton @Provides
    fun providePrefs(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
}