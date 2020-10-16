package com.viyatek.facefind.NetworkRequestHelpers

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import com.android.billingclient.api.Purchase
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.viyatek.billinghelper.BillingHelperLibraryRequestQueue
import com.viyatek.facefind.Interfaces.SubscriptionVerificationDataFetched
import com.viyatek.facefind.helpers.KotlinSharedPrefHandler
import com.viyatek.facefind.helpers.SubscribeCheck
import org.json.JSONObject


class SubscriptionVerification(
    val context: Context,
    val listener: SubscriptionVerificationDataFetched
) {

    fun ExecuteNetWorkCall(endpoint: String, purchase: Purchase) {


        val progressDialog = ProgressDialog(context)

        progressDialog.setMessage("Please wait while we are validating your purchase")
        progressDialog.show()
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

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


                if (progressDialog.isShowing) {
                    progressDialog.cancel()
                }
                val jsonPart: JSONObject = response.getJSONObject(0)

                val returnedToken = jsonPart.getString("purchase_token")
                val expiryTimeMillis = jsonPart.getLong("expiryTimeMillis")

                if (purchase.purchaseToken == returnedToken) {
                    Log.i("Subscription", "Bravo tokens are same")
                } else {
                    Log.i("Subscription", "Bravo tokens are different")
                }


                val isSubscribed =
                    SubscribeCheck(context).CheckSubscription(
                        KotlinSharedPrefHandler(context),
                        returnedToken,
                        expiryTimeMillis,
                        1,
                        purchase.sku
                    )


                if (isSubscribed) {
                    val sharedPrefHandler = KotlinSharedPrefHandler(context)

                    sharedPrefHandler.ApplyPrefs(sharedPrefHandler.SUBSCRIPTION_TYPE, purchase.sku)

                    listener.SubscriptionVerified(purchase)

                } else {
                    listener.SubscriptionVerificationFailed(null)
                }

            },
            { error ->

                if (progressDialog.isShowing) {
                    progressDialog.cancel()
                }

                listener.SubscriptionVerificationFailed(error)
            }
        )

        jsonObjectRequest.setShouldCache(false)
        jsonObjectRequest.retryPolicy =
            DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

// Access the RequestQueue through your singleton class.
        BillingHelperLibraryRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest)

    }
}