package com.viyatek.facefind.NetworkRequestHelpers

import android.content.Context
import android.net.Uri
import com.android.billingclient.api.Purchase
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.viyatek.billinghelper.BillingHelperLibraryRequestQueue
import com.viyatek.facefind.Interfaces.SubscriptionPaymentProblem
import com.viyatek.facefind.helpers.KotlinSharedPrefHandler
import com.viyatek.facefind.helpers.SubscribeCheck
import org.json.JSONObject


class SubscriptionDataFetch(val context: Context, val listener: SubscriptionPaymentProblem) {

    fun ExecuteNetWorkCall(endpoint:String, purchase: Purchase) {

        var paymentStateint = 1
        //will get url
        //will get url

        val url = Uri.parse(endpoint)
            .buildUpon()
            .appendQueryParameter("token",purchase.purchaseToken)
            .appendQueryParameter("subscriptionId",purchase.sku)
            .appendQueryParameter("packageName", context.applicationContext.packageName)
            .build()

// Formulate the request and handle the response.

        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.POST, url.toString(), null,
            { response ->

                val jsonPart: JSONObject = response.getJSONObject(0)

                val returnedToken = jsonPart.getString("purchase_token")
                val expiryTimeMillis = jsonPart.getLong("expiryTimeMillis")

                if (jsonPart.has("paymentState")) {
                    val paymentState = jsonPart.getString("paymentState")
                    try {
                        paymentStateint = Integer.valueOf(paymentState)
                    } catch (e: Exception) {
                        paymentStateint = 1
                    }
                }

                val subscriptionId = jsonPart.getString("subscription_id")

                SubscribeCheck(context).CheckSubscription(
                    KotlinSharedPrefHandler(context),
                    returnedToken,
                    expiryTimeMillis,
                    paymentStateint,
                    purchase.sku
                )

                if (paymentStateint == 0 )
                {
                    //todo handle grace period payment problem
                }

                if (!purchase.isAcknowledged) {
                    listener.PurchaseNotAcknowledged(purchase)
                }

            },
            { error -> }
        )

        jsonObjectRequest.setShouldCache(false)
        jsonObjectRequest.retryPolicy =
            DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

// Access the RequestQueue through your singleton class.
        BillingHelperLibraryRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest)

    }
}