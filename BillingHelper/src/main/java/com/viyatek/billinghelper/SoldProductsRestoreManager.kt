package com.viyatek.billinghelper

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.viyatek.helper.BillingHelper.BaseBillingClass
import com.viyatek.helper.BillingHelper.IRestoreSubscription

class SoldProductsRestoreManager(context: Context, val subscriptionRestoreListener : IRestoreSubscription) : BaseBillingClass(context) {

    fun init()
    { startProcess()}

    override fun ConnectedGooglePlay() {
        QuerySubscriptions()
    }

    override fun PurchaseCanceledByUser(purchase: Purchase?) {
       // TODO("Not yet implemented")
    }

    override fun PurchaseError(purchase: Purchase?, billingResponseCode: Int) {
       // TODO("Not yet implemented")
    }

    override fun HandlePurchase(purchase: Purchase) {
       // TODO("Not yet implemented")
    }

    private fun QuerySubscriptions() {
        val purchasesResult = getBillingClient().queryPurchases(BillingClient.SkuType.SUBS)

        var activePurchase: Purchase

        val boughtPurchases = purchasesResult.purchasesList

        if (boughtPurchases != null && boughtPurchases.size > 0) {

            activePurchase = boughtPurchases[0]

            for (i in boughtPurchases.indices) {
                if (boughtPurchases[i].purchaseTime > activePurchase.purchaseTime) {
                    activePurchase = boughtPurchases[i]
                }
            }

            //make interface call
            //Active Purchase Found
            subscriptionRestoreListener.ActiveSubscriptionDataFetched(activePurchase)

        } else {
            subscriptionRestoreListener.NoActiveSubscriptionCanFetched()
            //NoSubsCanBeFound Make a Pref Check
        }
    }
}