package com.viyatek.facefind.helpers

import android.content.Context
import android.util.Log

import java.util.*

class SubscribeCheck(val mCtxt: Context) {

    private var calendar: Calendar=Calendar.getInstance()


    fun CheckSubscription(
        sharedPrefsHandler: KotlinSharedPrefHandler,
        token: String,
        expirationTime: Long,
        paymentStatus: Int,
        SubscriptionType: String
    ): Boolean {


        Log.i("Subscription", SubscriptionType)
        sharedPrefsHandler.ApplyPrefs(sharedPrefsHandler.SUBSCRIPTION_TYPE, SubscriptionType)

        //IF RECOVERED WE WILL REGISTER PERSON WİTH NEW ID
        return if (expirationTime < calendar.timeInMillis) {

            CancelSubscription(sharedPrefsHandler).CancelSubscribe()

            false
        } else if (expirationTime > calendar.timeInMillis && paymentStatus == 0) {

            ExecuteSubscription(sharedPrefsHandler).SubscribeClient(token, expirationTime)
            true
        } else {

            ExecuteSubscription(sharedPrefsHandler).SubscribeClient(token, expirationTime)

            true
        }
    }
}