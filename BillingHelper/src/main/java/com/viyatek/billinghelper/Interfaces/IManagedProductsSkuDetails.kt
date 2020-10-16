package com.viyatek.billinghelper.Interfaces

import com.android.billingclient.api.SkuDetails

interface IManagedProductsSkuDetails {
    fun ManagedProductsSkuFetched(subsciptionSkuDetailsList :List<SkuDetails>)
    fun ManagedProductsSkuFetchedError(billingResponseCode : Int)
}