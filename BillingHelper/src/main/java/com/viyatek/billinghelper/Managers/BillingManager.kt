package com.viyatek.billinghelper.Managers

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.billingclient.api.Purchase
import com.viyatek.billinghelper.Handlers.*
import com.viyatek.helper.BillingHelper.BaseBillingClass
import com.viyatek.helper.BillingHelper.InAppPurchaseListener

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
                 AckHandler(getBillingClient()).AcknowledgePurchase(purchase)
             } else {
                 inAppPurchaseListener.SubscriptionPurchaseSucceded(purchase)
                 AckHandler(getBillingClient()).AcknowledgePurchase(purchase)
             }
         } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
             //handle pending state
             Toast.makeText(context, "You have a pending purchase", Toast.LENGTH_LONG).show()
         }
     }

    private fun queryPurchases() {
        QuerySubscriptionHandler(getBillingClient() , inAppPurchaseListener).querySubscriptions()
        QueryManagedProductsHandler(getBillingClient() , inAppPurchaseListener).queryInAppProducts()
    }

    private fun querySkuDetails() {

        Log.d("Subscription", "Sent Sku Names to Server " + subs_skuList.size)

        QuerySubscriptionSkuHandler(getBillingClient() , subs_skuList, inAppPurchaseListener).querySkuDetails()
        QueryManagedProductsSkuHandler(getBillingClient() , managedProductsSkuList , inAppPurchaseListener).querySkuDetails()
    }

}