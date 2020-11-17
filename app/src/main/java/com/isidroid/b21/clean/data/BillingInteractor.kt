package clean.data

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.core.content.edit
import com.android.billingclient.api.*
import com.isidroid.b21.clean.domain.IBillingUseCase
import timber.log.Timber
import javax.inject.Inject

private const val KEY_PREMIUM = "is_premium"

class BillingInteractor @Inject constructor(
    private val prefs: SharedPreferences
) : IBillingUseCase, PurchasesUpdatedListener,
    BillingClientStateListener {
    private lateinit var billingClient: BillingClient
    override val skuNamePremium = "premium"
    override val allDetails = mutableSetOf<IBillingUseCase.Sku>()
    override val listeners = mutableSetOf<IBillingUseCase.Listener>()
    override var isReady: Boolean = false
    override var isPremium: Boolean = true
  //  prefs.getBoolean(KEY_PREMIUM, false)
    //    set(value) {
      //      prefs.edit(commit = true) { putBoolean(KEY_PREMIUM, value) }
       //     field = value
        //}

    override fun create(context: Context) = apply {
        if (!::billingClient.isInitialized) {
            billingClient = BillingClient.newBuilder(context)
                .enablePendingPurchases()
                .setListener(this)
                .build()

            billingClient.startConnection(this)
        }
    }

    override fun purchase(activity: Activity) {
        querySku(
            sku = skuNamePremium,
            callback = { details ->
                Timber.i("purchase $details")

                details?.apply {
                    val flowParams = BillingFlowParams.newBuilder().setSkuDetails(this).build()
                    billingClient.launchBillingFlow(activity, flowParams)
                }
            }
        )
    }

    private fun checkPurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) acknowledge(purchase)
            else onPurchased()
        }
    }

    private fun acknowledge(purchase: Purchase) {
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.acknowledgePurchase(params) {
            Timber.i("onAcknowledgePurchase isOk=${it.responseCode == BillingClient.BillingResponseCode.OK}")
            if (it.responseCode == BillingClient.BillingResponseCode.OK) onPurchased()
        }
    }

    private fun querySku(sku: String, callback: (SkuDetails?) -> Unit) {
        val params = SkuDetailsParams.newBuilder()
            .setSkusList(listOf(sku))
            .setType(BillingClient.SkuType.INAPP)

        billingClient.querySkuDetailsAsync(params.build())
        { billingResult, skuDetailsList ->
            val ok = billingResult.responseCode == BillingClient.BillingResponseCode.OK
            Timber.i("querySku ok=$ok, details=$skuDetailsList")
            if (ok)
                skuDetailsList?.firstOrNull { it.sku == sku }
                    ?.let {
                        allDetails.add(
                            IBillingUseCase.Sku(
                                title = it.title,
                                name = it.sku,
                                description = it.description,
                                price = it.price,
                                icon = it.iconUrl
                            )
                        )
                        callback(it)
                    }
        }
    }


    // BillingClientStateListener
    override fun onBillingServiceDisconnected() {}
    override fun onBillingSetupFinished(billingResult: BillingResult) {
        val isOk = billingResult.responseCode == BillingClient.BillingResponseCode.OK

        if (isOk) {
            isReady = true
            val purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP)
            purchasesResult.purchasesList?.firstOrNull { it.sku == skuNamePremium }
                ?.let { checkPurchase(it) }
                ?: run { isPremium = false }
        }
    }

    // PurchasesUpdatedListener
    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null)
            for (purchase in purchases) checkPurchase(purchase)
    }
}