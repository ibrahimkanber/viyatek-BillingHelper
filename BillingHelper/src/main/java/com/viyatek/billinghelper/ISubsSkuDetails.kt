package com.viyatek.helper.BillingHelper

import com.android.billingclient.api.SkuDetails

interface ISubsSkuDetails {

    fun SubSkuFetched(subsciptionSkuDetailsList :List<SkuDetails>)
    fun SubSkuFetchedError(billingResponseCode : Int)
}