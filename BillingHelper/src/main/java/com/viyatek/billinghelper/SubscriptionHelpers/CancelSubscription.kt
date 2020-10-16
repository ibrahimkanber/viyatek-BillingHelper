package com.viyatek.facefind.helpers

class CancelSubscription(val sharedPrefHandler: KotlinSharedPrefHandler) {

    fun CancelSubscribe() {
        sharedPrefHandler.ApplyPrefs(sharedPrefHandler.SUBSCRIBED, 0)
        sharedPrefHandler.ApplyPrefs(sharedPrefHandler.SUBSCRIPTION_TYPE, "not_subscribed")
        sharedPrefHandler.ApplyPrefs(sharedPrefHandler.SUBSCRIPTION_TOKEN, "0")
    }
}