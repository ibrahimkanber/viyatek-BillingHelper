package com.viyatek.helper.BillingHelper

import com.android.billingclient.api.Purchase

interface IRestoreSubscription {
    fun ActiveSubscriptionDataFetched(purchase : Purchase)
    fun NoActiveSubscriptionCanFetched()
}