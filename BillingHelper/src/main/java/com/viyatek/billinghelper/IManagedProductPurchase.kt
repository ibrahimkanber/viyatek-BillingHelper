package com.viyatek.billinghelper

import com.android.billingclient.api.Purchase

interface IManagedProductPurchase {
    fun ManagedProductPurchaseSucceded(purchase : Purchase)
}