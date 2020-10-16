package com.viyatek.billinghelper.Interfaces

import com.android.billingclient.api.Purchase

interface IRestoreManagedProducts {
    fun SoldManagedProductsFetched(purchase : Purchase)
}