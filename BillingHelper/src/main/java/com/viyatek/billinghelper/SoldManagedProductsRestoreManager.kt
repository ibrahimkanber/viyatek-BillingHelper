package com.viyatek.billinghelper

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.viyatek.helper.BillingHelper.BaseBillingClass
import com.viyatek.helper.BillingHelper.IRestoreManagedProducts


class SoldManagedProductsRestoreManager(context: Context, private val managedProductsRestoreListener: IRestoreManagedProducts) : BaseBillingClass(context) {

    fun init() {startProcess()}

    override fun ConnectedGooglePlay() {
        queryInAppProducts()
    }

    private fun queryInAppProducts() {
        val inApppurchasesResult = getBillingClient().queryPurchases(BillingClient.SkuType.INAPP)

        val boughtManagedProducts = inApppurchasesResult.purchasesList

        if (boughtManagedProducts != null && boughtManagedProducts.size > 0) {
            for (purchase in boughtManagedProducts) {

                managedProductsRestoreListener.SoldManagedProductsFetched(purchase)

            }
        }
    }

}