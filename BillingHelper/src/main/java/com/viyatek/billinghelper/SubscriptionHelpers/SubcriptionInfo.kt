package com.viyatek.billinghelper.SubscriptionHelpers

import android.content.Context
import com.viyatek.facefind.helpers.KotlinSharedPrefHandler

class SubcriptionInfo(val context: Context) {

    fun isSubscribed() : Boolean {
        val prefHandler = KotlinSharedPrefHandler(context)
        return prefHandler.GetPref(prefHandler.SUBSCRIBED)?.getIntegerValue() == 1
    }

    fun getSubscriptionToken() : String? {
        val prefHandler = KotlinSharedPrefHandler(context)
        return prefHandler.GetPref(prefHandler.SUBSCRIPTION_TOKEN)?.getStringValue()
    }

    fun getExpireTime() : Long? {
        val prefHandler = KotlinSharedPrefHandler(context)
        return prefHandler.GetPref(prefHandler.SUBSCRIPTION_EXPIRATION_DATE)?.getStringValue()?.toLong()
    }

    fun getSubscriptionSku() : String? {
        val prefHandler = KotlinSharedPrefHandler(context)
        return prefHandler.GetPref(prefHandler.SUBSCRIPTION_TYPE)?.getStringValue()
    }

}