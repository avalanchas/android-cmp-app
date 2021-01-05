package com.sourcepoint.gdpr_cmplibrary.v6.ccpa

import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib
import com.sourcepoint.gdpr_cmplibrary.v6.client.CCPAClient

interface CCPAConsentLibClient : GDPRConsentLib {
    fun setClient(gdpr : CCPAClient)
}