package com.sourcepoint.gdpr_cmplibrary.v6.builder

import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib
import com.sourcepoint.gdpr_cmplibrary.NativeMessage
import com.sourcepoint.gdpr_cmplibrary.v6.client.CCPAClient
import com.sourcepoint.gdpr_cmplibrary.v6.client.GDPRClient

interface GDPRConsentLibClientSecond : GDPRConsentLib {
    fun setClient(gdpr : GDPRClient)
}

fun createSecond() : GDPRConsentLibClientSecond = GDPRConsentLibClientSecondImpl()

internal class GDPRConsentLibClientSecondImpl : GDPRConsentLibClientSecond{
    override fun setClient(gdpr: GDPRClient) {
        TODO("Not yet implemented")
    }

    override fun loadMessage() {
        TODO("Not yet implemented")
    }

    override fun loadMessage(nativeMessage: NativeMessage) {
        TODO("Not yet implemented")
    }

    override fun loadPrivacyManager() {
        TODO("Not yet implemented")
    }
}