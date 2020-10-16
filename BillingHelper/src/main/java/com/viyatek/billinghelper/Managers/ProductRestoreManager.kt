package com.viyatek.billinghelper.Managers

import android.content.Context
import com.viyatek.billinghelper.Handlers.QueryManagedProductsHandler
import com.viyatek.billinghelper.Handlers.QuerySubscriptionHandler
import com.viyatek.billinghelper.Interfaces.ProductRestoreListener
import com.viyatek.helper.BillingHelper.BaseBillingClass

class ProductRestoreManager (context: Context, private val productRestoreListener: ProductRestoreListener) :
    BaseBillingClass(context) {

    fun init() { startProcess() }

    override fun ConnectedGooglePlay() {
        QuerySubscriptionHandler(getBillingClient() , productRestoreListener).querySubscriptions()
        QueryManagedProductsHandler(getBillingClient() , productRestoreListener).queryInAppProducts()
    }

}