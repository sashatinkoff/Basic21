package com.isidroid.b21.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.isidroid.b21.clean.data.BillingInteractor
import com.isidroid.b21.clean.data.LotteryUseCaseImpl
import com.isidroid.b21.clean.data.SessionInteractor
import com.isidroid.b21.clean.domain.IBillingUseCase
import com.isidroid.b21.clean.domain.ISessionUseCase
import com.isidroid.b21.clean.domain.LotteryRepository
import com.isidroid.b21.clean.domain.LotteryUseCase
import com.isidroid.b21.clean.repository.LotteryRepositoryImpl
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

    @JvmStatic @Singleton @Provides
    fun provideSessionUseCase(): ISessionUseCase = SessionInteractor()

    @JvmStatic @Singleton @Provides
    fun provideLotteryUseCase(context: Context, repository: LotteryRepository): LotteryUseCase =
        LotteryUseCaseImpl(context, repository)

    @JvmStatic @Singleton @Provides
    fun provideLotteryRepository(): LotteryRepository = LotteryRepositoryImpl()
}