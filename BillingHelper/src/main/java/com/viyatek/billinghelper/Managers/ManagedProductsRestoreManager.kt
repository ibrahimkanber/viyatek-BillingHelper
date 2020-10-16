package com.viyatek.billinghelper.Managers

import android.content.Context
import com.viyatek.billinghelper.Handlers.QueryManagedProductsHandler
import com.viyatek.helper.BillingHelper.BaseBillingClass
import com.viyatek.helper.BillingHelper.IRestoreManagedProducts


class ManagedProductsRestoreManager(context: Context, private val managedProductsRestoreListener: IRestoreManagedProducts) : BaseBillingClass(context) {

    fun init() {startProcess()}

    override fun ConnectedGooglePlay() {
        QueryManagedProductsHandler(getBillingClient(), managedProductsRestoreListener).queryInAppProducts()
    }

}