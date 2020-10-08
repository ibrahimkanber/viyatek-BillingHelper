package com.viyatek.helper.BillingHelper

import com.android.billingclient.api.SkuDetails

interface IManagedProductsSkuDetails {

    fun ManagedProductsSkuFetched(subsciptionSkuDetailsList :List<SkuDetails>)
    fun ManagedProductsSkuFetchedError(billingResponseCode : Int)
}