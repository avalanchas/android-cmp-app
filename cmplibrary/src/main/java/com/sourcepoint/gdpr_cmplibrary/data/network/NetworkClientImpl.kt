package com.sourcepoint.gdpr_cmplibrary.data.network

import com.sourcepoint.gdpr_cmplibrary.data.executeOnLeft
import com.sourcepoint.gdpr_cmplibrary.data.map
import com.sourcepoint.gdpr_cmplibrary.data.network.model.NativeMessageReq
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

fun createNetworkClient(): NetworkClient = NetworkClientImpl()

private class NetworkClientImpl(
    private val httpClient: OkHttpClient = OkHttpClient(),
    val url: String = "https://cdn.privacy-mgmt.com/wrapper/tcfv2/v1/gdpr/native-message",
    private val responseManager: ResponseManager = createResponseManager()
) : NetworkClient {

    private val mainScope by lazy { CoroutineScope(Dispatchers.Main) }
    private val workerScope by lazy { CoroutineScope(Dispatchers.IO) }

    override fun getNativeMessage(
        nativeMessageReq: NativeMessageReq,
        success: (JSONObject) -> Unit,
        error: (Throwable) -> Unit) {
        val mediaType = MediaType.parse("application/json")
        val body: RequestBody = RequestBody.create(mediaType, nativeMessageReq.toBodyRequest())

        val request: Request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        httpClient
            .newCall(request)
            .enqueue {
                onFailure { _, exception -> error(exception) }
                onResponse { _, r ->
                    responseManager
                        .parseResponse(r)
                        .map { success(it) }
                        .executeOnLeft { error(it) }
                }
            }
    }
}