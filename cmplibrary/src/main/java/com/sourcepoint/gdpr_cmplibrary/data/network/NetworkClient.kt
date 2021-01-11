package com.sourcepoint.gdpr_cmplibrary.data.network

import com.sourcepoint.gdpr_cmplibrary.data.network.model.NativeMessageReq
import com.sourcepoint.gdpr_cmplibrary.data.network.model.NativeMessageWebResp

interface NetworkClient {
    fun getNativeMessage(
        nativeMessageReq: NativeMessageReq,
        success: (NativeMessageWebResp) -> Unit,
        error: (Throwable) -> Unit
    )
}