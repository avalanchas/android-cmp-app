package com.sourcepoint.gdpr_cmplibrary.v6

import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib
import com.sourcepoint.gdpr_cmplibrary.v6.client.CCPAClient
import com.sourcepoint.gdpr_cmplibrary.v6.client.GDPRClient

interface GDPRConsentLibClient : GDPRConsentLib {
    fun setClient(gdpr : GDPRClient)
}