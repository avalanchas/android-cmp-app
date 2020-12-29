package com.sourcepoint.gdpr_cmplibrary.v6

import android.content.Context
import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib

fun createGDPRConsentLib(
    accountId : Int,
    property: String,
    propertyId : Int,
    pmId: String,
    context : Context
) : GDPRConsentLib = GDPRConsentLibImplV6(accountId, property, propertyId, pmId, context)