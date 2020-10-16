package com.viyatek.facefind.Interfaces

import com.android.billingclient.api.Purchase
import com.android.volley.VolleyError

interface SubscriptionVerificationDataFetched {
    fun SubscriptionVerified(purchase: Purchase)
    fun SubscriptionVerificationFailed(error: VolleyError?)
}