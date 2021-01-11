package com.sourcepoint.gdpr_cmplibrary.data.network

import com.sourcepoint.gdpr_cmplibrary.data.Either
import com.sourcepoint.gdpr_cmplibrary.data.network.model.NativeMessageReq
import com.sourcepoint.gdpr_cmplibrary.data.network.model.NativeMessageWebResp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

internal class NetworkClientImpl(
    private val httpClient: OkHttpClient = OkHttpClient()
) : NetworkClient {

    private val mainScope by lazy { CoroutineScope(Dispatchers.Main) }
    private val workerScope by lazy { CoroutineScope(Dispatchers.IO) }

    override fun getNativeMessage(
        nativeMessageReq: NativeMessageReq,
        success: (NativeMessageWebResp) -> Unit,
        error: (Throwable) -> Unit) {
        val mediaType = MediaType.parse("application/json")
        val body: RequestBody = RequestBody.create(mediaType, nativeMessageReq.toBodyRequest())

        val request: Request = Request.Builder()
            .url("https://cdn.privacy-mgmt.com/wrapper/tcfv2/v1/gdpr/native-message")
            .post(body)
            .build()

        httpClient
            .newCall(request)
            .enqueue {
                onFailure { call, exception ->
                    exception.printStackTrace()
                }
                onResponse { call, r -> }
            }
    }

    suspend fun suspendedGetNativeMessage(nativeMessageReq: NativeMessageReq): Either<NativeMessageWebResp> {

        val mediaType = MediaType.parse("application/json")
        val body: RequestBody = RequestBody.create(mediaType, nativeMessageReq.toBodyRequest())

        val request: Request = Request.Builder()
            .url("https://cdn.privacy-mgmt.com/wrapper/tcfv2/v1/gdpr/native-message")
            .post(body)
            .build()

        val response = httpClient.newCall(request).execute()
        when (response.isSuccessful) {
            false -> println(response)
            else -> {
                val jsonObj = JSONObject(response.body()!!.string())
                Thread.currentThread().name
                println(jsonObj)
            }
        }
        return Either.Left(RuntimeException())
    }
}