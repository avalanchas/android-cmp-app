package com.sourcepoint.gdpr_cmplibrary.v6.ccpa

import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib
import com.sourcepoint.gdpr_cmplibrary.v6.client.CCPAClient
import com.sourcepoint.gdpr_cmplibrary.v6.client.Client

interface CCPAConsentLibClient : GDPRConsentLib {
    fun setClient(client : Client)
}