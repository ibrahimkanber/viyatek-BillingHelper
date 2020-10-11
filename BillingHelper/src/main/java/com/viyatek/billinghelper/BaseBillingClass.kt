package com.viyatek.helper.BillingHelper

import android.content.Context
import android.util.Log
import com.android.billingclient.api.*

abstract class BaseBillingClass (context: Context) {
    private var isConnected: Boolean = false

    protected abstract fun ConnectedGooglePlay()



    private val purchaseUpdateListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {
                    handlePurchase(purchase)
                }
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                purchaseCanceledByUser(purchases?.get(0))
                // Handle an error caused by a user cancelling the purchase flow.
            } else {
                purchaseError(purchases?.get(0), billingResult.responseCode)
                // Handle any other error codes.
            }
        }

    private var billingClient = BillingClient.newBuilder(context)
        .setListener(purchaseUpdateListener)
        .enablePendingPurchases()
        .build()

    private fun SetUpConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

                    isConnected = true
                    Log.d("Subscription", "Connected to the server")

                    ConnectedGooglePlay()
                }
            }

            override fun onBillingServiceDisconnected() {
                isConnected = false
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    protected fun startProcess() {SetUpConnection()}

    protected open fun handlePurchase(purchase: Purchase) {}
    protected open fun getBillingClient() : BillingClient {return billingClient}
    protected open fun getConnectionStatus() : Boolean {return  isConnected}
    protected open fun purchaseCanceledByUser(purchase: Purchase?) {}
    protected open fun purchaseError (purchase: Purchase?, billingResponseCode: Int) {}

}