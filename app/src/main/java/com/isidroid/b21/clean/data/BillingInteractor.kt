package com.isidroid.b21.clean.data

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.isidroid.b21.clean.domain.IBillingUseCase
import javax.inject.Inject

private const val KEY_PREMIUM = "is_premium"

class BillingInteractor @Inject constructor(
    private val prefs: SharedPreferences
) : IBillingUseCase {
    override val skuNamePremium = "premium"
    override val allDetails = mutableSetOf<IBillingUseCase.Sku>()
    override val listeners = mutableSetOf<IBillingUseCase.Listener>()
    override var isReady: Boolean = false
    override var isPremium: Boolean = true

    override fun create(context: Context) = apply {
    }

    override fun purchase(activity: Activity) {
        TODO("Not yet implemented")
    }
}
