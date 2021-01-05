package com.sourcepoint.example_app

import android.content.Context
import com.sourcepoint.gdpr_cmplibrary.v6.BuilderV6
import com.sourcepoint.gdpr_cmplibrary.v6.gdpr.GDPRConsentLibClient
import com.sourcepoint.gdpr_cmplibrary.v6.ccpa.CCPAConsentLibClient
import com.sourcepoint.gdpr_cmplibrary.v6.client.CCPAClient
import com.sourcepoint.gdpr_cmplibrary.v6.client.GDPRClient
import com.sourcepoint.gdpr_cmplibrary.v6.createLegislationObj

class Test{
    fun myTest(context : Context){

        /**
         * First option
         */
        val gdprObj = BuilderV6()
            .setAccountId(1)
            .setContex(context)
            .setProperty("")
            .setPropertyId(1)
            .build(GDPRConsentLibClient::class.java)

        gdprObj.setClient(object : GDPRClient{})

        /**
         * Second option
         */
        val ccpaObj = createLegislationObj<CCPAConsentLibClient>(1, "2", 3, "4", context)
        ccpaObj.setClient(object : CCPAClient{})
    }
}