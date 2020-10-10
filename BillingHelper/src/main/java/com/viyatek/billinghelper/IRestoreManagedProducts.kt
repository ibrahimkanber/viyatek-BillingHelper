package com.viyatek.helper.BillingHelper

import com.android.billingclient.api.Purchase

interface IRestoreManagedProducts {

    fun SoldManagedProductsFetched(purchase : Purchase)
}