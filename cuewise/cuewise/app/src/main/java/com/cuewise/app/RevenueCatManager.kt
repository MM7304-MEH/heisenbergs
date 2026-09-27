package com.cuewise.app

import android.app.Activity
import android.content.Context
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import com.revenuecat.purchases.models.Package

/**
 * Small, testable boundary around RevenueCat.
 *
 * Development builds use the Test Store API key from Gradle properties. Before
 * any real store release, replace it with the platform-specific key and verify
 * the product in the platform sandbox.
 */
object RevenueCatManager {
    const val PRO_ENTITLEMENT = "cuewise_pro"

    private var configured = false

    fun configure(context: Context) {
        val apiKey = BuildConfig.REVENUECAT_API_KEY.trim()
        if (apiKey.isBlank() || configured) return

        Purchases.configure(
            PurchasesConfiguration.Builder(context, apiKey).build()
        )
        configured = true
    }

    fun isConfigured(): Boolean = configured

    fun loadPackages(
        onSuccess: (List<Package>) -> Unit,
        onError: (String) -> Unit
    ) {
        if (!configured) {
            onError("RevenueCat is not configured yet. Add a Test Store API key to run a purchase test.")
            return
        }

        Purchases.sharedInstance.getOfferings(
            onError = { error -> onError(error.message) },
            onSuccess = { offerings ->
                onSuccess(offerings.current?.availablePackages.orEmpty())
            }
        )
    }

    fun purchase(
        activity: Activity,
        packageToBuy: Package,
        onSuccess: (CustomerInfo) -> Unit,
        onError: (String) -> Unit
    ) {
        if (!configured) {
            onError("RevenueCat is not configured yet.")
            return
        }

        Purchases.sharedInstance.purchase(
            packageToPurchase = packageToBuy,
            onError = { error, userCancelled ->
                if (!userCancelled) onError(error.message)
            },
            onSuccess = { _, customerInfo -> onSuccess(customerInfo) }
        )
    }

    fun restore(
        onSuccess: (CustomerInfo) -> Unit,
        onError: (String) -> Unit
    ) {
        if (!configured) {
            onError("RevenueCat is not configured yet.")
            return
        }

        Purchases.sharedInstance.restorePurchases(
            onError = { error -> onError(error.message) },
            onSuccess = onSuccess
        )
    }
}
