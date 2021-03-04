package com.isidroid.b21.clean.data

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.isidroid.b21.clean.domain.IBillingUseCase

class BillingInteractor(prefs: SharedPreferences) : IBillingUseCase {
    override val skuNamePremium: String = "premium"
    override val allDetails: MutableSet<IBillingUseCase.Sku> = mutableSetOf()
    override val listeners: MutableSet<IBillingUseCase.Listener> = mutableSetOf()
    override var isPremium: Boolean = true
    override var isReady = true

    override fun create(context: Context): IBillingUseCase = apply { }
    override fun purchase(activity: Activity) {}
}