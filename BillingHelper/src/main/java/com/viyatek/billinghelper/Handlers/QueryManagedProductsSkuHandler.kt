package com.viyatek.billinghelper.Handlers

import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.SkuDetailsParams
import com.viyatek.billinghelper.Interfaces.IManagedProductsSkuDetails
import com.viyatek.billinghelper.Interfaces.InAppPurchaseListener

class QueryManagedProductsSkuHandler(private val billingClient: BillingClient, private val managedProductsSkuList : List<String>) {

    private lateinit var listener : IManagedProductsSkuDetails

    constructor(billingClient: BillingClient, managedProductsSkuList: List<String>, inAppPurchaseListener: InAppPurchaseListener) : this(billingClient, managedProductsSkuList)
    {
        listener = inAppPurchaseListener
    }

    constructor(billingClient: BillingClient,managedProductsSkuList : List<String>, subscriptionPurchaseListener: IManagedProductsSkuDetails) : this(billingClient, managedProductsSkuList)
    {
        listener = subscriptionPurchaseListener
    }

    fun querySkuDetails() {

        Log.d("Subscription", "Sent Sku Names to Server " + managedProductsSkuList.size)

        val sku_params = SkuDetailsParams.newBuilder()

        sku_params.setSkusList(managedProductsSkuList).setType(BillingClient.SkuType.INAPP)
        billingClient.querySkuDetailsAsync(sku_params.build()) { billingResult, skuDetailsList ->

            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {

                listener.ManagedProductsSkuFetched(skuDetailsList)
            }
        }
    }
}