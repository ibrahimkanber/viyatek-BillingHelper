package com.viyatek.billinghelper.Managers

import android.content.Context
import com.viyatek.billinghelper.Handlers.QuerySubscriptionSkuHandler
import com.viyatek.helper.BillingHelper.BaseBillingClass
import com.viyatek.helper.BillingHelper.ISubsSkuDetails

class SubscriptionSkuDetailsManager(context: Context, val subscriptionSkuDetailsListener : ISubsSkuDetails) : BaseBillingClass(context) {

    private var subs_skuList = arrayListOf<String>()

    fun init(subs_skuList: ArrayList<String>) {
        this.subs_skuList = subs_skuList
        startProcess()
    }

    override fun ConnectedGooglePlay() { QuerySubscriptionSkuHandler(getBillingClient(), subs_skuList, subscriptionSkuDetailsListener).querySkuDetails() }
}