package com.sourcepoint.gdpr_cmplibrary.v6.ccpa

import android.content.Context
import com.sourcepoint.gdpr_cmplibrary.NativeMessage
import com.sourcepoint.gdpr_cmplibrary.v6.client.CCPAClient
import com.sourcepoint.gdpr_cmplibrary.v6.client.Client
import org.json.JSONObject

fun createCCPA(
    accountId: Int,
    property: String,
    propertyId: Int,
    pmId: String,
    context: Context
): CCPAConsentLibClient = CCPAConsentLibImplV6(accountId, property, propertyId, pmId, context)

internal class CCPAConsentLibImplV6(
    val accountId : Int,
    val property: String,
    val propertyId : Int,
    val pmId: String,
    val context : Context
) : CCPAConsentLibClient {

    lateinit var c: Client

    override fun run() {
        class User(name : String)
        JSONObject()
    }

    override fun loadMessage(nativeMessage: NativeMessage) {
        TODO("Not yet implemented")
    }

    override fun showPm() {

    }

    override fun setClient(c: Client) {
        this.c = c
    }
}