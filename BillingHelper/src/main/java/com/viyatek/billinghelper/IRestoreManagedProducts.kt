package com.viyatek.helper.BillingHelper

import com.android.billingclient.api.Purchase

interface IRestoreManagedProducts {
    fun ManagedProductPurchaseSucceded(purchase : Purchase)
    fun SoldManagedProductsFetched(purchase : Purchase)
}