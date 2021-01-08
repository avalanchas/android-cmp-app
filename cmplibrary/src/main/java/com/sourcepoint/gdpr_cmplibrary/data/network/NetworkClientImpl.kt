package com.sourcepoint.gdpr_cmplibrary.data.network

import com.beust.klaxon.Klaxon
import com.sourcepoint.gdpr_cmplibrary.data.network.model.NativeMessageWeb

internal class NetworkClientImpl : NetworkClient {
    override fun getNativeMessage(): NativeMessageWeb {
        return Klaxon()
            .parse<NativeMessageWeb>("""
                {
                    "msgJSON": {}
                }
            """.trimIndent())!!
    }
}