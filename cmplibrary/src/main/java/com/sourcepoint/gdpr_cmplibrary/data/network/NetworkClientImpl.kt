package com.sourcepoint.gdpr_cmplibrary.data.network

import com.beust.klaxon.Klaxon
import com.sourcepoint.gdpr_cmplibrary.data.network.model.NativeMessageReq
import com.sourcepoint.gdpr_cmplibrary.data.network.model.NativeMessageWeb
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody

internal class NetworkClientImpl : NetworkClient {
    override fun getNativeMessage(nativeMessage : NativeMessageReq): NativeMessageWeb {
        val mediaType = MediaType.parse("application/json")
        val body: RequestBody = RequestBody.create(mediaType, nativeMessage.toString())

        val request: Request = Request.Builder()
            .url("https://cdn.privacy-mgmt.com/wrapper/tcfv2/v1/gdpr/native-message")
            .post(body)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        return Klaxon()
            .parse<NativeMessageWeb>("""
                {
                    "msgJSON": {}
                }
            """.trimIndent())!!
    }
}