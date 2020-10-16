package com.viyatek.billinghelper.Interfaces

import com.android.billingclient.api.Purchase

interface ISubscriptionPurchase {
    fun SubscriptionPurchaseSucceded(purchase: Purchase)
}