# viyatek-BillingHelper

Billing helper is a kotlin helper library of Google Play Billing Library to make it enable to use it without extra configurations. It's aim is getting rid of the boiler-plate codes to use the Google Play Billing Library.

After Implementing BillingHelper. You will be able to:

- Handle your whole purchase process (back end server(for verification and validatiob) is not included)
- Get Sku Details
- Restore Purchases

wtihout extra effort.

Presequities :
--------------
You need to implement Google Play Billing Library to your project

implementation 'com.android.billingclient:billing-ktx:3.0.1'

To Handle Whole Puchase Process :
---------------------------------
This class handle whole process if you have both subscription and managed products. 

        val billingManager = BillingManager(requireContext(), object  : InAppPurchaseListener {
            override fun ActiveSubscriptionDataFetched(purchase: Purchase) {
                // You should restore active subscription at this point
            }

            override fun NoActiveSubscriptionCanFetched() {
                // To handle the situation when the user has no active subscription
            }

            override fun SubSkuFetched(subsciptionSkuDetailsList: List<SkuDetails>) {
                // To handle the process when subscription Sku details fetched. Can be use to product details to the user (price etc...)
            }

            override fun SubSkuFetchedError(billingResponseCode: Int) {
                // can be used to log the details when requested skus can't be retrieved from Google Play
         
            }

            override fun SoldManagedProductsFetched(purchase: Purchase) {
                 // To restore managed products sales
            }

            override fun ManagedProductsSkuFetched(subsciptionSkuDetailsList: List<SkuDetails>) {
                 // To handle the process when managed products Sku details fetched. Can be use to product details to the user (price, sub period etc...)
         
            }

            override fun ManagedProductsSkuFetchedError(billingResponseCode: Int) {
                // can be used to log the details when requested skus can't be retrieved from Google Play
            }

            override fun SubscriptionPurchaseSucceded(purchase: Purchase) {
               // To do handle successful subscription Purchase. Best practice is making http request to back end server to validate or verify the subscription.
            }

            override fun ManagedProductPurchaseSucceded(purchase: Purchase) {
                // To do handle successful managed product purchase
            }

        })
        
        billingManager.init( SubsSkuList , ManagedProductSkuList )
