package com.viyatek.facefind.Interfaces

import com.android.billingclient.api.Purchase

interface SubscriptionPaymentProblem {
    fun PurchaseNotAcknowledged(purchase: Purchase)
}