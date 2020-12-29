package com.sourcepoint.gdpr_cmplibrary.v6

import android.content.Context

internal class GDPRConsentLibImplV6(
    val accountId : Int,
    val property: String,
    val propertyId : Int,
    val pmId: String,
    val context : Context
) : GDPRConsentLibClient{

    lateinit var gdpr: GDPRClient
    lateinit var ccpa: CCPAClient

    override fun run() {

    }

    override fun showPm() {

    }

    override fun setGDPRClient(gdpr: GDPRClient) {
        this.gdpr = gdpr
    }

    override fun setCCPAClient(ccpa: CCPAClient) {
        this.ccpa = ccpa
    }
}