package com.sourcepoint.example_app

import android.content.Context
import com.sourcepoint.gdpr_cmplibrary.v6.BuilderV6
import com.sourcepoint.gdpr_cmplibrary.v6.ccpa.CCPAConsentLibClient
import com.sourcepoint.gdpr_cmplibrary.v6.client.CCPAClient
import com.sourcepoint.gdpr_cmplibrary.v6.client.Client
import com.sourcepoint.gdpr_cmplibrary.v6.client.GDPRClient
import com.sourcepoint.gdpr_cmplibrary.v6.createLegislationObj
import com.sourcepoint.gdpr_cmplibrary.v6.gdpr.GDPRConsentLibClient

class Test{
    fun myTest(context : Context){

        /**
         * First option
         */
        val gdprObj = BuilderV6()
            .setAccountId(1)
            .setContext(context)
            .setProperty("")
            .setPropertyId(1)
            .build(GDPRConsentLibClient::class.java)

        // set the generic client
        gdprObj.setClient(object : Client {})
        // set the specific client
        gdprObj.setClient(object : GDPRClient{})

        /**
         * Second option
         */
        val ccpaObj = createLegislationObj<CCPAConsentLibClient>(1, "2", 3, "4", context)

        // set the generic client
        ccpaObj.setClient(object : Client {})
        // set the specific client
        ccpaObj.setClient(object : CCPAClient{})
    }
}