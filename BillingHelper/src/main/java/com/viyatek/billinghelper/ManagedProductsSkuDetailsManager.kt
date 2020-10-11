package com.viyatek.billinghelper

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.SkuDetailsParams
import com.viyatek.helper.BillingHelper.BaseBillingClass
import com.viyatek.helper.BillingHelper.IManagedProductsSkuDetails

class ManagedProductsSkuDetailsManager(context: Context, val managedProductsSkuDetailsListener : IManagedProductsSkuDetails) : BaseBillingClass(context) {

    private var managedProducts_skuList = arrayListOf<String>()

    fun init(managedProducts_skuList : ArrayList<String>) {
       this.managedProducts_skuList = managedProducts_skuList
    }

    override fun ConnectedGooglePlay() { querySkuDetails() }

    private fun querySkuDetails() {

        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(managedProducts_skuList).setType(BillingClient.SkuType.INAPP)

        getBillingClient().querySkuDetailsAsync(
            params.build()
        ) { billingResult, skuDetailsList ->
            // Process the result.

            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null)
            {
                managedProductsSkuDetailsListener.ManagedProductsSkuFetched(skuDetailsList)
            }
            else
            {
                managedProductsSkuDetailsListener.ManagedProductsSkuFetchedError(billingResult.responseCode)
            }
        }

    }
}