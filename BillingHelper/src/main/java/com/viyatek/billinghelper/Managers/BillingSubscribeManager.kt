package com.viyatek.billinghelper.Managers

import android.content.Context
import android.widget.Toast
import com.android.billingclient.api.Purchase
import com.viyatek.billinghelper.Handlers.AckHandler
import com.viyatek.billinghelper.Handlers.QuerySubscriptionHandler
import com.viyatek.billinghelper.Handlers.QuerySubscriptionSkuHandler
import com.viyatek.billinghelper.Interfaces.SubscriptionListener
import com.viyatek.helper.BillingHelper.BaseBillingClass

class BillingSubscribeManager(private val context: Context, private val subscriptionListener: SubscriptionListener) : BaseBillingClass(context) {

    var subscriptionSkuList = arrayListOf<String>()

    fun init(subscriptionSkuList : ArrayList<String>) {
        this.subscriptionSkuList = subscriptionSkuList
        super.startProcess()
    }

     override fun ConnectedGooglePlay() {
         QuerySubscriptionHandler(getBillingClient(), subscriptionListener).querySubscriptions()
         QuerySubscriptionSkuHandler(getBillingClient(), subscriptionSkuList, subscriptionListener).querySkuDetails()
     }

     override fun handlePurchase(purchase: Purchase) {
         val sku = purchase.sku

         if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED ) {
             if(subscriptionSkuList.contains(sku))
             {
                 subscriptionListener.SubscriptionPurchaseSucceded(purchase)
                 AckHandler(getBillingClient()).AcknowledgePurchase(purchase)
             }

         } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
             //handle pending state
             Toast.makeText(context, "You have a pending purchase", Toast.LENGTH_LONG).show()
         }
     }


 }