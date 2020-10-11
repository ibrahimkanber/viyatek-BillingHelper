package com.viyatek.billinghelper

import android.content.Context
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.SkuDetailsParams
import com.viyatek.helper.BillingHelper.BaseBillingClass

class ProductSkusManager(context: Context, private val productSkuListener : ProductsSkuListener): BaseBillingClass(context) {

    private var subs_skuList: List<String> = ArrayList()
    private var managedProductsSkuList : List<String> = ArrayList()

    fun init(subscriptionSkuList: List<String>, managedProductsSkuList: List<String>) {
        this.subs_skuList = subscriptionSkuList
        this.managedProductsSkuList = managedProductsSkuList
        super.startProcess()
    }

    override fun ConnectedGooglePlay() {
        querySkuDetails()
    }

    private fun querySkuDetails() {

        Log.d("Subscription", "Sent Sku Names to Server " + subs_skuList.size)

        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(subs_skuList).setType(BillingClient.SkuType.SUBS)

        getBillingClient().querySkuDetailsAsync(
            params.build()
        ) { billingResult, skuDetailsList ->
            // Process the result.

            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null)
            {
                productSkuListener.SubSkuFetched(skuDetailsList)
            }
            else
            {
                productSkuListener.SubSkuFetchedError(billingResult.responseCode)
            }

        }

        val sku_params = SkuDetailsParams.newBuilder()

        sku_params.setSkusList(managedProductsSkuList).setType(BillingClient.SkuType.INAPP)
        getBillingClient().querySkuDetailsAsync(sku_params.build()) { billingResult, skuDetailsList ->

            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {

                productSkuListener.ManagedProductsSkuFetched(skuDetailsList)
            }
            else
            {
                productSkuListener.ManagedProductsSkuFetchedError(billingResult.responseCode)
            }
        }


    }

}