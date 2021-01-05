package com.sourcepoint.gdpr_cmplibrary.v6.gdpr

import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib
import com.sourcepoint.gdpr_cmplibrary.v6.client.GDPRClient

interface GDPRConsentLibClient : GDPRConsentLib {
    fun setClient(gdpr : GDPRClient)
}