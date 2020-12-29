package com.sourcepoint.gdpr_cmplibrary.v6

import android.content.Context
import com.sourcepoint.gdpr_cmplibrary.v6.client.CCPAClient
import com.sourcepoint.gdpr_cmplibrary.v6.client.GDPRClient
import com.sourcepoint.gdpr_cmplibrary.v6.data.network.NetworkClient
import com.sourcepoint.gdpr_cmplibrary.v6.data.parser.JSONParser
import org.json.JSONObject

internal class CCPAConsentLibImplV6(
    val accountId : Int,
    val property: String,
    val propertyId : Int,
    val pmId: String,
    val context : Context
) : GDPRConsentLibClient{

    lateinit var gdpr: GDPRClient
    lateinit var ccpa: CCPAClient

    override fun loadMessage() {
        class User(name : String)
        JSONObject()
    }

    override fun loadPrivacyManager() {

    }

    override fun setClient(gdpr: GDPRClient) {
        this.gdpr = gdpr
    }
}