package com.viyatek.billinghelper

import android.content.Context
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.SkuDetailsParams
import com.viyatek.helper.BillingHelper.BaseBillingClass
import com.viyatek.helper.BillingHelper.ISubsSkuDetails

class SubscriptionSkuDetailsManager(context: Context, val subscriptionSkuDetailsListener : ISubsSkuDetails) : BaseBillingClass(context) {

    private val subs_skuList = arrayListOf<String>()

    fun init() {

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
                subscriptionSkuDetailsListener.SubSkuFetched(skuDetailsList)
            }
            else
            {
                subscriptionSkuDetailsListener.SubSkuFetchedError(billingResult.responseCode)
            }

        }

    }
}