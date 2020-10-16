package com.viyatek.billinghelper.Managers

import android.content.Context
import com.viyatek.billinghelper.Handlers.QuerySubscriptionHandler
import com.viyatek.billinghelper.Interfaces.IRestoreSubscription
import com.viyatek.helper.BillingHelper.BaseBillingClass

class SubscriptionRestoreManager(context: Context, val subscriptionRestoreListener : IRestoreSubscription) : BaseBillingClass(context) {

    fun init() {startProcess()}
    override fun ConnectedGooglePlay() {  QuerySubscriptionHandler(getBillingClient(), subscriptionRestoreListener).querySubscriptions() }

}