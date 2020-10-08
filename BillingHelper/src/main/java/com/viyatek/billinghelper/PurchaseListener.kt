package com.viyatek.helper.BillingHelper

import com.android.billingclient.api.Purchase

interface PurchaseListener {


    fun PurchaseCanceledByUser (purchase: Purchase?)
    fun PurchaseError (purchase: Purchase? , billingResponseCode : Int)

}