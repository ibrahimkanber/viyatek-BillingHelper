package com.viyatek.facefind.helpers

class ExecuteSubscription(val sharedPrefsHandler: KotlinSharedPrefHandler) {

    fun SubscribeClient(token: String, expireTime: Long) {
        sharedPrefsHandler.ApplyPrefs(sharedPrefsHandler.SUBSCRIBED, 1)
        sharedPrefsHandler.ApplyPrefs(sharedPrefsHandler.SUBSCRIPTION_TOKEN, token)
        sharedPrefsHandler.ApplyPrefs(sharedPrefsHandler.SUBSCRIPTION_EXPIRATION_DATE, expireTime.toString())
    }
}