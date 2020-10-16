package com.viyatek.billinghelper.Managers

import android.content.Context
import com.viyatek.billinghelper.Handlers.QueryManagedProductsSkuHandler
import com.viyatek.billinghelper.Handlers.QuerySubscriptionSkuHandler
import com.viyatek.billinghelper.ProductsSkuListener
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
        QuerySubscriptionSkuHandler(getBillingClient() , subs_skuList, productSkuListener).querySkuDetails()
        QueryManagedProductsSkuHandler(getBillingClient() , managedProductsSkuList , productSkuListener).querySkuDetails()
    }


}