package com.sourcepoint.cmplibrary.legislation.ccpa

import com.sourcepoint.gdpr_cmplibrary.NativeMessage

internal class CCPAConsentLibImpl : CCPAConsentLib {
    override var spCcpaClient: SpCCPAClient?
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun loadMessage() {
        TODO("Not yet implemented")
    }

    override fun loadMessage(authId: String?) {
        TODO("Not yet implemented")
    }

    override fun loadMessage(nativeMessage: NativeMessage) {
        TODO("Not yet implemented")
    }

    override fun loadMessage(authId: String, nativeMessage: NativeMessage) {
        TODO("Not yet implemented")
    }

    override fun loadPrivacyManager() {
        TODO("Not yet implemented")
    }

    override fun loadPrivacyManager(authId: String) {
        TODO("Not yet implemented")
    }
}