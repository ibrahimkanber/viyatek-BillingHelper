package com.viyatek.helper.BillingHelper

import com.viyatek.billinghelper.IManagedProductPurchase
import com.viyatek.billinghelper.ISubscriptionPurchase

interface InAppPurchaseListener : IRestoreSubscription, ISubsSkuDetails, PurchaseListener,
    IRestoreManagedProducts, IManagedProductsSkuDetails, ISubscriptionPurchase, IManagedProductPurchase