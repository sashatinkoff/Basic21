package com.isidroid.b21.clean.domain

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.annotation.CallSuper

interface IBillingUseCase {
    val skuNamePremium: String
    val allDetails: MutableSet<Sku>
    val listeners: MutableSet<Listener>
    var isPremium: Boolean
    var isReady: Boolean

    fun create(context: Context): IBillingUseCase

    fun addListener(listener: Listener) = apply { listeners.add(listener) }
    fun removeListener(listener: Listener) = apply { listeners.remove(listener) }

    @CallSuper
    fun onPurchased() {
        listeners.forEach { it.onPurchase(true) }
        isPremium = true
    }

    fun purchase(activity: Activity)
    fun showAds(context: Context, adView: View) {
//        if (adView !is AdView || isPremium) return
//
//        val adRequest = AdRequest.Builder().build()
//        adView.loadAd(adRequest)
    }

    data class Sku(
        val name: String,
        val title: String,
        val price: String,
        val icon: String? = null,
        val description: String
    )

    interface Listener {
        fun onPurchase(isPremium: Boolean)
    }
}