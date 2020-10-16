package com.viyatek.billinghelper.Managers

import android.content.Context
import android.widget.Toast
import com.android.billingclient.api.Purchase
import com.viyatek.billinghelper.Handlers.AckHandler
import com.viyatek.billinghelper.Handlers.QueryManagedProductsHandler
import com.viyatek.billinghelper.Handlers.QueryManagedProductsSkuHandler
import com.viyatek.helper.BillingHelper.BaseBillingClass
import com.viyatek.helper.BillingHelper.ManagedProductListener

class BillingManageProductManager(
    val context: Context,
    private val managedProductListener: ManagedProductListener
) : BaseBillingClass(context) {

    private var managedProductSkuList = arrayListOf<String>()

    fun init(managedProductSkuList: ArrayList<String>) {
        this.managedProductSkuList = managedProductSkuList
        super.startProcess()
    }

    override fun ConnectedGooglePlay() {
        QueryManagedProductsHandler(getBillingClient(), managedProductListener).queryInAppProducts()
        QueryManagedProductsSkuHandler(getBillingClient(),managedProductSkuList, managedProductListener).querySkuDetails()
    }

    override fun handlePurchase(purchase: Purchase) {

        val sku = purchase.sku

        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED ) {
            if(managedProductSkuList.contains(sku))
            {
                managedProductListener.ManagedProductPurchaseSucceded(purchase)
                AckHandler(getBillingClient()).AcknowledgePurchase(purchase)
            }

        } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
            //handle pending state
            Toast.makeText(context, "You have a pending purchase", Toast.LENGTH_LONG).show()
        }

         }


}