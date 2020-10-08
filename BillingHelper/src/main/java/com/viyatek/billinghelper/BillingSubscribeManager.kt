package com.viyatek.helper.BillingHelper

import android.content.Context
import android.widget.Toast
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetailsParams

class BillingSubscribeManager(val context: Context,val subscriptionListener: SubscriptionListener) : BaseBillingClass(context) {

    var subscriptionSkuList = arrayListOf<String>()

    fun init(subscriptionSkuList : ArrayList<String>) {
        this.subscriptionSkuList = subscriptionSkuList
        super.startProcess()
    }

     override fun ConnectedGooglePlay() {

         querySubscriptions()
         querySkuDetails()

     }

     override fun PurchaseCanceledByUser(purchase: Purchase?) {
         subscriptionListener.PurchaseCanceledByUser(purchase)
     }

     override fun PurchaseError(purchase: Purchase?, billingResponseCode: Int) {
         subscriptionListener.PurchaseError(purchase , billingResponseCode)
     }

     override fun HandlePurchase(purchase: Purchase) {
         val sku = purchase.sku

         if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED ) {
             if(subscriptionSkuList.contains(sku))
             subscriptionListener.SubscriptionPurchaseSucceded(purchase)
         } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
             //handle pending state
             Toast.makeText(context, "You have a pending purchase", Toast.LENGTH_LONG).show()
         }
     }

     private fun querySubscriptions() {

         val purchasesResult =  getBillingClient().queryPurchases(BillingClient.SkuType.SUBS)

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
             subscriptionListener.ActiveSubscriptionDataFetched(activePurchase)

         } else {
             subscriptionListener.NoActiveSubscriptionCanFetched()
             //NoSubsCanBeFound Make a Pref Check
         }
     }

     private fun querySkuDetails() {

        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(subscriptionSkuList).setType(BillingClient.SkuType.SUBS)

        getBillingClient().querySkuDetailsAsync(
            params.build()
        ) { billingResult, skuDetailsList ->
            // Process the result.

            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null)
            {
                subscriptionListener.SubSkuFetched(skuDetailsList)
            }
            else
            {
                subscriptionListener.SubSkuFetchedError(billingResult.responseCode)
            }

        }

    }
 }