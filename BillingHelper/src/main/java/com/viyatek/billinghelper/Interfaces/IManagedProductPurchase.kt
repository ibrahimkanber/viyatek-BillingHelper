package com.viyatek.billinghelper.Interfaces

import com.android.billingclient.api.Purchase

interface IManagedProductPurchase {
    fun ManagedProductPurchaseSucceded(purchase : Purchase)
}