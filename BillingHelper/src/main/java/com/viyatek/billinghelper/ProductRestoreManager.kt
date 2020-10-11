package com.viyatek.billinghelper

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.viyatek.helper.BillingHelper.BaseBillingClass

class ProductRestoreManager (context: Context, private val productRestoreListener: ProductRestoreListener) :
    BaseBillingClass(context) {

    fun init() { startProcess() }

    override fun ConnectedGooglePlay() {
        querySubscriptions()
    }
    private fun querySubscriptions() {
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
            productRestoreListener.ActiveSubscriptionDataFetched(activePurchase)
            queryInAppProducts()

        } else {
            productRestoreListener.NoActiveSubscriptionCanFetched()
            queryInAppProducts()
            //NoSubsCanBeFound Make a Pref Check
        }
    }
    private fun queryInAppProducts() {
        val inApppurchasesResult = getBillingClient().queryPurchases(BillingClient.SkuType.INAPP)

        val boughtManagedProducts = inApppurchasesResult.purchasesList

        if (boughtManagedProducts != null && boughtManagedProducts.size > 0) {
            for (purchase in boughtManagedProducts) {

                productRestoreListener.SoldManagedProductsFetched(purchase)

            }
        }
    }
}