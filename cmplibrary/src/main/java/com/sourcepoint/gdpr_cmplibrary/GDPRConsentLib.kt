package com.sourcepoint.gdpr_cmplibrary

interface GDPRConsentLib {
    fun run()
    fun loadMessage(nativeMessage : NativeMessage)
    @Deprecated("This will be deprecated")
    fun showPm()
}