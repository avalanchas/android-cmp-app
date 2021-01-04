package com.sourcepoint.gdpr_cmplibrary

interface GDPRConsentLib {
    fun loadMessage()
    fun loadMessage(nativeMessage : NativeMessage)
    @Deprecated("This will be deprecated")
    fun loadPrivacyManager()
}