package com.viyatek.billinghelper.Handlers

import com.android.billingclient.api.BillingClient
import com.viyatek.helper.BillingHelper.IRestoreManagedProducts
import com.viyatek.helper.BillingHelper.InAppPurchaseListener

internal class QueryManagedProductsHandler(private val billingClient: BillingClient) {

    private lateinit var listener : IRestoreManagedProducts

    constructor(billingClient: BillingClient, inAppPurchaseListener: InAppPurchaseListener) : this(billingClient)
    {
        listener = inAppPurchaseListener
    }

    constructor(billingClient: BillingClient, managedProductListener: IRestoreManagedProducts) : this(billingClient)
    {
        listener = managedProductListener
    }

    fun queryInAppProducts() {

        val inApppurchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP)

        val boughtManagedProducts = inApppurchasesResult.purchasesList

        if (boughtManagedProducts != null && boughtManagedProducts.size > 0) {
            for (purchase in boughtManagedProducts) {

                listener.SoldManagedProductsFetched(purchase)
                AckHandler(billingClient).AcknowledgePurchase(purchase)

            }
        }
    }
}