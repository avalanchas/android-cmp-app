package com.sourcepoint.gdpr_cmplibrary.v6

import android.content.Context
import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib
import com.sourcepoint.gdpr_cmplibrary.exception.Legislation
import com.sourcepoint.gdpr_cmplibrary.exception.Legislation.CCPA
import com.sourcepoint.gdpr_cmplibrary.exception.Legislation.GDPR
import com.sourcepoint.gdpr_cmplibrary.v6.data.network.NetworkClientImpl
import com.sourcepoint.gdpr_cmplibrary.v6.data.parser.JSONParserImpl

fun createConsentLib(
    legislation: Legislation,
    accountId: Int,
    property: String,
    propertyId: Int,
    pmId: String,
    context: Context
): GDPRConsentLib = when (legislation) {
    GDPR -> GDPRConsentLibImplV6(
        accountId,
        property,
        propertyId,
        pmId,
        context
    )
    CCPA -> CCPAConsentLibImplV6(
        accountId,
        property,
        propertyId,
        pmId,
        context
    )
}