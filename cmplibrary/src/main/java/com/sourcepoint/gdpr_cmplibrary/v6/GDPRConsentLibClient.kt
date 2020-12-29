package com.sourcepoint.gdpr_cmplibrary.v6

import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib

interface GDPRConsentLibClient : GDPRConsentLib {
    fun setGDPRClient(gdpr : GDPRClient)
    fun setCCPAClient(ccpa : CCPAClient)
}