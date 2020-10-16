package com.viyatek.billinghelper.Interfaces

import com.android.billingclient.api.Purchase

interface IRestoreSubscription {
    fun ActiveSubscriptionDataFetched(purchase : Purchase)
    fun NoActiveSubscriptionCanFetched()
}