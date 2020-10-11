package com.viyatek.helper.BillingHelper

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetailsParams

class BillingManageProductManager(
    context: Context,
    private val managedProductListener: ManagedProductListener
) : BaseBillingClass(context) {

    private var managedProductSkuList = arrayListOf<String>()

    fun init(managedProductSkuList: ArrayList<String>) {
        this.managedProductSkuList = managedProductSkuList
        super.startProcess()
    }

    override fun ConnectedGooglePlay() {
        queryManagedProductSales()
        querySkuDetails()
    }

    private fun queryManagedProductSales() {

        val purchasesResult = getBillingClient().queryPurchases(BillingClient.SkuType.INAPP)

        val boughtPurchases = purchasesResult.purchasesList


        if (boughtPurchases != null && boughtPurchases.size > 0) {

            if (boughtPurchases.size > 0) {
                for (purchase in boughtPurchases) {

                    managedProductListener.SoldManagedProductsFetched(purchase)
                }
            }
        }
    }

    private fun querySkuDetails() {

        val sku_params = SkuDetailsParams.newBuilder()

            sku_params.setSkusList(managedProductSkuList).setType(BillingClient.SkuType.INAPP)
        getBillingClient().querySkuDetailsAsync(sku_params.build()) { billingResult, skuDetailsList ->

            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {

                managedProductListener.ManagedProductsSkuFetched(skuDetailsList)
            }
        }


    }


    override fun handlePurchase(purchase: Purchase) {
        managedProductListener.ManagedProductPurchaseSucceded(purchase)
    }


}