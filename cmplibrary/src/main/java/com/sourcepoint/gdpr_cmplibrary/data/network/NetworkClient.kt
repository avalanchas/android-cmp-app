package com.sourcepoint.gdpr_cmplibrary.data.network

import com.sourcepoint.gdpr_cmplibrary.data.network.model.NativeMessageReq
import com.sourcepoint.gdpr_cmplibrary.data.network.model.NativeMessageWeb

interface NetworkClient {
    fun getNativeMessage(nativeMessage : NativeMessageReq) : NativeMessageWeb
}

fun createNetClient() : NetworkClient = NetworkClientImpl()