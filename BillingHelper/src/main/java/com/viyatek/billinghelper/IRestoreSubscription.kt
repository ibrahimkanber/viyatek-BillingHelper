package com.viyatek.helper.BillingHelper

import com.android.billingclient.api.Purchase

interface IRestoreSubscription {
    fun SubscriptionPurchaseSucceded(purchase: Purchase)
    fun ActiveSubscriptionDataFetched(purchase : Purchase)
    fun NoActiveSubscriptionCanFetched()
}