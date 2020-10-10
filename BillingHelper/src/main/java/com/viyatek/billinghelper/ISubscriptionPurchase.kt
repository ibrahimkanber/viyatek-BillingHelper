package com.viyatek.billinghelper

import com.android.billingclient.api.Purchase

interface ISubscriptionPurchase {
    fun SubscriptionPurchaseSucceded(purchase: Purchase)
}