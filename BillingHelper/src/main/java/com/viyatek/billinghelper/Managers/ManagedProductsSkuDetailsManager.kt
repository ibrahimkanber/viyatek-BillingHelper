package com.viyatek.billinghelper.Managers

import android.content.Context
import com.viyatek.billinghelper.Handlers.QueryManagedProductsSkuHandler
import com.viyatek.billinghelper.Interfaces.IManagedProductsSkuDetails
import com.viyatek.helper.BillingHelper.BaseBillingClass

class ManagedProductsSkuDetailsManager(context: Context, val managedProductsSkuDetailsListener : IManagedProductsSkuDetails) : BaseBillingClass(context) {

    private var managedProducts_skuList = arrayListOf<String>()

    fun init(managedProducts_skuList : ArrayList<String>) {
       this.managedProducts_skuList = managedProducts_skuList
    }

    override fun ConnectedGooglePlay() {   QueryManagedProductsSkuHandler(getBillingClient(),managedProducts_skuList, managedProductsSkuDetailsListener).querySkuDetails() }

}