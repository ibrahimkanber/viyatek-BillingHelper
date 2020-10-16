package com.viyatek.billinghelper.Handlers

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.viyatek.helper.BillingHelper.IRestoreSubscription
import com.viyatek.helper.BillingHelper.InAppPurchaseListener


internal class QuerySubscriptionHandler(private val billingClient: BillingClient) {

    private lateinit var listener : IRestoreSubscription

    constructor(billingClient: BillingClient, inAppPurchaseListener: InAppPurchaseListener) : this(billingClient)
    {
        listener = inAppPurchaseListener
    }

    constructor(billingClient: BillingClient, subscriptionPurchaseListener: IRestoreSubscription) : this(billingClient)
    {
        listener = subscriptionPurchaseListener
    }

      fun querySubscriptions() {
        val purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS)

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
            listener.ActiveSubscriptionDataFetched(activePurchase)
            AckHandler(billingClient).AcknowledgePurchase(activePurchase)


        } else {
            listener.NoActiveSubscriptionCanFetched()
            //NoSubsCanBeFound Make a Pref Check
        }
    }
}