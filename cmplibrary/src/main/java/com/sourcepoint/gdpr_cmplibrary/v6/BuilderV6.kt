package com.sourcepoint.gdpr_cmplibrary.v6

import android.content.Context
import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib
import com.sourcepoint.gdpr_cmplibrary.v6.ccpa.CCPAConsentLibClient
import com.sourcepoint.gdpr_cmplibrary.v6.gdpr.GDPRConsentLibClient
import com.sourcepoint.gdpr_cmplibrary.v6.ccpa.createCCPA
import com.sourcepoint.gdpr_cmplibrary.v6.gdpr.createGDPR

class BuilderV6 {

    private var accountId: Int? = null
    private var property: String? = null
    private var propertyId: Int? = null
    private var pmId: String? = null
    private var context: Context? = null

    fun setAccountId(accountId: Int) = apply {
        this.accountId = accountId
    }

    fun setProperty(property : String) = apply {
        this.property = property
    }

    fun setPmId(pmId : String) = apply {
        this.pmId = pmId
    }

    fun setPropertyId(propertyId : Int) = apply {
        this.propertyId = propertyId
    }

    fun setContext(context : Context) = apply {
        this.context = context
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : GDPRConsentLib> build(clazz: Class<out T>): T {
        checkParameter()
        return when (clazz) {

            GDPRConsentLibClient::class.java -> (createGDPR(accountId!!, property!!, propertyId!!, pmId!!, context!!) as? T)
                ?: fail("this")

            CCPAConsentLibClient::class.java -> (createCCPA(accountId!!, property!!, propertyId!!, pmId!!, context!!) as? T)
                ?: fail("this")

            else -> fail(clazz.name)
        }

    }

    private fun checkParameter(){
        accountId ?: failParam("accountId")
        pmId ?: failParam("pmId")
        context ?: failParam("context")
        propertyId ?: failParam("propertyId")
        property ?: failParam("property")
    }

    private fun fail(m : String): Nothing = throw RuntimeException("Invalid class exception. $m is not an available option.")
    private fun failParam(p : String): Nothing = throw RuntimeException("$p cannot be null!!!")
}