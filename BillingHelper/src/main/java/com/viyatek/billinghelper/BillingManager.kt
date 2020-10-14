package com.viyatek.helper.BillingHelper

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetailsParams

class BillingManager(private val context: Context, private val inAppPurchaseListener: InAppPurchaseListener) : BaseBillingClass(context) {


    private var isConnected: Boolean = false
    private var subs_skuList: List<String> = ArrayList()
    private var managedProductsSkuList : List<String> = ArrayList()

    fun init(subscriptionSkuList: List<String>, managedProductsSkuList: List<String>) {
       this.subs_skuList = subscriptionSkuList
       this.managedProductsSkuList = managedProductsSkuList
       super.startProcess()
    }

    override fun ConnectedGooglePlay() {
        queryPurchases()
        querySkuDetails()
    }

    override fun handlePurchase(purchase: Purchase) {
         val sku = purchase.sku

         if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
             if (managedProductsSkuList.contains(sku)) {
                 inAppPurchaseListener.ManagedProductPurchaseSucceded(purchase)
             } else {
                 inAppPurchaseListener.SubscriptionPurchaseSucceded(purchase)
             }
         } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
             //handle pending state
             Toast.makeText(context, "You have a pending purchase", Toast.LENGTH_LONG).show()
         }
     }

    private fun queryPurchases() {
        querySubscriptions()
        queryInAppProducts()
    }

    private fun queryInAppProducts() {
        val inApppurchasesResult = getBillingClient().queryPurchases(BillingClient.SkuType.INAPP)

        val boughtManagedProducts = inApppurchasesResult.purchasesList

        if (boughtManagedProducts != null && boughtManagedProducts.size > 0) {
            for (purchase in boughtManagedProducts) {

                inAppPurchaseListener.SoldManagedProductsFetched(purchase)

            }
        }
    }

    private fun querySubscriptions() {
        val purchasesResult = getBillingClient().queryPurchases(BillingClient.SkuType.SUBS)

        var activePurchase: Purchase

        val boughtPurchases = purchasesResult.purchasesList

        if (boughtPurchases != null && boughtPurchases.size > 0) {

            activePurchase = boughtPurchases[0]

            for (i in boughtPurchases.indices) {
                if (boughtPurchases[i].purchaseTime > activePurchase.purchaseTime) {
                    activePurchase = boughtPurchases[i]
                }
            }

            //make interface call
            //Active Purchase Found
            inAppPurchaseListener.ActiveSubscriptionDataFetched(activePurchase)


        } else {
            inAppPurchaseListener.NoActiveSubscriptionCanFetched()
            //NoSubsCanBeFound Make a Pref Check
        }
    }

    private fun querySkuDetails() {

        Log.d("Subscription", "Sent Sku Names to Server " + subs_skuList.size)

        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(subs_skuList).setType(BillingClient.SkuType.SUBS)

        getBillingClient().querySkuDetailsAsync(
            params.build()
        ) { billingResult, skuDetailsList ->
            // Process the result.

            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null)
            {
                inAppPurchaseListener.SubSkuFetched(skuDetailsList)
            }
            else
            {
                inAppPurchaseListener.SubSkuFetchedError(billingResult.responseCode)
            }

        }

        val sku_params = SkuDetailsParams.newBuilder()

        sku_params.setSkusList(managedProductsSkuList).setType(BillingClient.SkuType.INAPP)
        getBillingClient().querySkuDetailsAsync(sku_params.build()) { billingResult, skuDetailsList ->

            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {

                inAppPurchaseListener.ManagedProductsSkuFetched(skuDetailsList)
            }
        }


    }

}