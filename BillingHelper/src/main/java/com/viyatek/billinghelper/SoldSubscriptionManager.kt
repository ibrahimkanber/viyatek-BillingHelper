package com.viyatek.billinghelper

import android.content.Context
import com.android.billingclient.api.Purchase
import com.viyatek.helper.BillingHelper.BaseBillingClass

class SoldSubscriptionManager(context: Context) : BaseBillingClass(context) {

    override fun ConnectedGooglePlay() {

       // TODO("Not yet implemented")
    }

    override fun PurchaseCanceledByUser(purchase: Purchase?) {
       // TODO("Not yet implemented")
    }

    override fun PurchaseError(purchase: Purchase?, billingResponseCode: Int) {
       // TODO("Not yet implemented")
    }

    override fun HandlePurchase(purchase: Purchase) {
       // TODO("Not yet implemented")
    }
}