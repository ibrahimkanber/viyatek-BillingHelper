package com.viyatek.billinghelper.Managers

import android.content.Context
import com.viyatek.billinghelper.Handlers.QuerySubscriptionHandler
import com.viyatek.helper.BillingHelper.BaseBillingClass
import com.viyatek.helper.BillingHelper.IRestoreSubscription

class SubscriptionRestoreManager(context: Context, val subscriptionRestoreListener : IRestoreSubscription) : BaseBillingClass(context) {

    fun init() {startProcess()}
    override fun ConnectedGooglePlay() {  QuerySubscriptionHandler(getBillingClient(), subscriptionRestoreListener).querySubscriptions() }

}