package com.viyatek.billinghelper.Managers

import android.content.Context
import com.viyatek.billinghelper.Handlers.QueryManagedProductsHandler
import com.viyatek.billinghelper.Interfaces.IRestoreManagedProducts
import com.viyatek.helper.BillingHelper.BaseBillingClass


class ManagedProductsRestoreManager(context: Context, private val managedProductsRestoreListener: IRestoreManagedProducts) : BaseBillingClass(context) {

    fun init() {startProcess()}

    override fun ConnectedGooglePlay() {
        QueryManagedProductsHandler(getBillingClient(), managedProductsRestoreListener).queryInAppProducts()
    }

}