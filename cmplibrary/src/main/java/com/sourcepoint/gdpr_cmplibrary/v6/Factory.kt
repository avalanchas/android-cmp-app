package com.sourcepoint.gdpr_cmplibrary.v6

import android.content.Context
import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib
import com.sourcepoint.gdpr_cmplibrary.v6.ccpa.CCPAConsentLibClient
import com.sourcepoint.gdpr_cmplibrary.v6.gdpr.GDPRConsentLibClient
import com.sourcepoint.gdpr_cmplibrary.v6.ccpa.createCCPA
import com.sourcepoint.gdpr_cmplibrary.v6.gdpr.createGDPR

inline fun <reified T : GDPRConsentLib> createLegislationObj(
    accountId: Int,
    property: String,
    propertyId: Int,
    pmId: String,
    context: Context
): T {
    return when (T::class.java) {

        GDPRConsentLibClient::class.java -> (createGDPR(accountId, property, propertyId, pmId, context) as? T)
            ?: throw RuntimeException("Invalid class exception")

        CCPAConsentLibClient::class.java -> (createCCPA(accountId, property, propertyId, pmId, context) as? T)
            ?: throw RuntimeException("Invalid class exception")

        else -> throw RuntimeException("Invalid class exception")
    }
}