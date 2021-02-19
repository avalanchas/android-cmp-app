package com.sourcepoint.cmplibrary

import android.content.Context
import android.view.View
import com.sourcepoint.cmplibrary.campaign.CampaignManager
import com.sourcepoint.cmplibrary.consent.ConsentManager
import com.sourcepoint.cmplibrary.core.layout.NativeMessageClient
import com.sourcepoint.cmplibrary.core.layout.nat.NativeMessage
import com.sourcepoint.cmplibrary.core.layout.nat.NativeMessageInternal
import com.sourcepoint.cmplibrary.core.web.ConsentWebView
import com.sourcepoint.cmplibrary.core.web.JSClientLib
import com.sourcepoint.cmplibrary.data.Service
import com.sourcepoint.cmplibrary.data.network.converter.JsonConverter
import com.sourcepoint.cmplibrary.data.network.model.ConsentAction
import com.sourcepoint.cmplibrary.data.network.util.HttpUrlManager
import com.sourcepoint.cmplibrary.data.network.util.HttpUrlManagerSingleton
import com.sourcepoint.cmplibrary.exception.GenericSDKException
import com.sourcepoint.cmplibrary.exception.Logger
import com.sourcepoint.cmplibrary.exception.MissingClientException
import com.sourcepoint.cmplibrary.exception.RenderingAppException
import com.sourcepoint.cmplibrary.model.ActionType
import com.sourcepoint.cmplibrary.model.PrivacyManagerTabK
import com.sourcepoint.cmplibrary.util.* // ktlint-disable

internal class SpConsentLibImpl(
    internal val pPrivacyManagerTab: PrivacyManagerTabK,
    internal val context: Context,
    internal val pLogger: Logger,
    internal val pJsonConverter: JsonConverter,
    internal val service: Service,
    internal val executor: ExecutorManager,
    private val pConnectionManager: ConnectionManager,
    private val viewManager: ViewsManager,
    private val campaignManager: CampaignManager,
    private val consentManager: ConsentManager,
    private val urlManager: HttpUrlManager = HttpUrlManagerSingleton
) : SpConsentLib {

    override var spClient: SpClient? = null
    private val nativeMsgClient by lazy { NativeMsgDelegate() }

    /** Start Client's methods */
    override fun loadMessage(authId: String) {
        checkMainThread("loadMessage")
        throwsExceptionIfClientNoSet()
        service.getMessage(
            messageReq = campaignManager.getMessageReq(),
            pSuccess = { messageResp -> },
            pError = { throwable -> }
        )
    }

    override fun loadMessage() {
        checkMainThread("loadMessage")
        throwsExceptionIfClientNoSet()

        if (viewManager.isViewInLayout) return

        service.getMessage(
            messageReq = campaignManager.getMessageReq(),
            pSuccess = { messageResp ->
                executor.executeOnMain {
                    val webView = viewManager.createWebView(this, JSReceiverDelegate())
                    (webView as? ConsentWebView)?.let { wv ->
                        messageResp
                            .campaigns
                            .mapNotNull { it.message }
                            .firstOrNull()
                            ?.let { jsonMessages ->
                                if (!consentManager.hasGdprConsent()) {
                                    wv.loadConsentUIFromUrl(urlManager.urlURenderingAppStage(), jsonMessages)
                                }
                            }
                            ?: run { logMess("{message json} is null for all the legislations") }
                    } ?: throw RuntimeException("webView is not a ConsentWebView")
                }
            },
            pError = { throwable ->
                spClient?.onError(throwable.toConsentLibException())
            }
        )
    }

    override fun loadMessage(nativeMessage: NativeMessage) {
        checkMainThread("loadMessage")
        throwsExceptionIfClientNoSet()

        service.getNativeMessageK(
            campaignManager.getMessageReq(),
            { messageResp ->
                executor.executeOnMain {
                    /** configuring onClickListener and set the parameters */
                    (nativeMessage as? NativeMessageInternal)?.setAttributes(messageResp.msg)
                    /** set the action callback */
                    (nativeMessage as? NativeMessageInternal)?.setActionClient(nativeMsgClient)
                    /** calling the client */
                    spClient?.onUIReady(nativeMessage)
                }
            },
            { throwable -> pLogger.error(throwable.toConsentLibException()) }
        )
    }

    override fun loadGDPRPrivacyManager() {
        checkMainThread("loadPrivacyManager")
        throwsExceptionIfClientNoSet()
        val pmConfig = campaignManager.getPmGDPRConfig()
        pmConfig
            .map {
                val webView = viewManager.createWebView(this, JSReceiverDelegate())
                webView?.loadConsentUIFromUrl(urlManager.urlPm(it))
            }
            .executeOnLeft { logMess("PmUrlConfig is null") }
    }

    override fun loadCCPAPrivacyManager() {
        checkMainThread("loadPrivacyManager")
        throwsExceptionIfClientNoSet()
    }

    override fun showView(view: View) {
        checkMainThread("showView")
        viewManager.showView(view)
    }

    override fun removeView(view: View) {
        checkMainThread("removeView")
        viewManager.removeView(view)
    }

    override fun dispose() {
        executor.dispose()
        viewManager.removeAllViews()
    }

    //    /** Start Receiver methods */
    inner class JSReceiverDelegate : JSClientLib {
        //
        override fun onConsentUIReady(view: View, isFromPM: Boolean) {
            // TODO what consent is ready? GDPR or CCPA?
            view.let { viewManager.showView(it) }
        }

        override fun log(view: View, tag: String?, msg: String?) {
            logMess("$tag $msg")
        }

        override fun log(view: View, msg: String?) {
            logMess("$msg")
        }

        override fun onError(view: View, errorMessage: String) {
            spClient?.onError(GenericSDKException(description = errorMessage))
            pLogger.error(RenderingAppException(description = errorMessage, pCode = errorMessage))
        }

        override fun onNoIntentActivitiesFoundFor(view: View, url: String) {
        }

        override fun onError(view: View, error: Throwable) {
            spClient?.onError(error)
        }

        override fun onAction(view: View, actionData: String) {
            /** spClient is called from [onActionFromWebViewClient] */
            pJsonConverter
                .toConsentAction(actionData)
                .map { onActionFromWebViewClient(it, view) }
                .executeOnLeft { throw it }
        }
    }

    /** End Receiver methods */

    private fun throwsExceptionIfClientNoSet() {
        spClient ?: throw MissingClientException(description = "spClient instance is missing")
    }

    /**
     * Receive the action performed by the user from the WebView
     */
    internal fun onActionFromWebViewClient(action: ConsentAction, view: View) {
        executor.executeOnMain {
            spClient?.onAction(view, action.actionType)
            when (action.actionType) {
                ActionType.MSG_CANCEL -> {
                    view.let { spClient?.onUIFinished(it) }
                }
                ActionType.SHOW_OPTIONS -> {
                    view.let { spClient?.onUIFinished(it) }
                }
                ActionType.PM_DISMISS -> {
                    view.let { spClient?.onUIFinished(it) }
                }
                ActionType.ACCEPT_ALL,
                ActionType.SAVE_AND_EXIT,
                ActionType.REJECT_ALL -> {
                    consentManager.buildGdprConsentReq(action)
                        .map { consentReq ->
                            service.sendConsent(
                                consentReq,
                                { consentResp -> consentManager.saveGdprConsent(consentResp.content) },
                                { throwable -> pLogger.error(throwable.toConsentLibException()) }
                            )
                        }
                        .executeOnLeft {
                            throw it
                        }
                    view.let { spClient?.onUIFinished(it) }
                }
            }
        }
    }

    private fun logMess(mess: String) = pLogger.d(this::class.java.simpleName, "========>  $mess")

    /**
     * Delegate used by the [NativeMessage] to catch events performed by the user
     */
    inner class NativeMsgDelegate : NativeMessageClient {

        override fun onClickAcceptAll(view: View, ca: ConsentAction) {
            spClient?.onAction(view, ActionType.ACCEPT_ALL)
        }

        override fun onClickRejectAll(view: View, ca: ConsentAction) {
            spClient?.onAction(view, ActionType.REJECT_ALL)
        }

        override fun onPmDismiss(view: View, ca: ConsentAction) {}

        override fun onClickShowOptions(view: View, ca: ConsentAction) {
            spClient?.onAction(view, ActionType.SHOW_OPTIONS)
        }

        override fun onClickCancel(view: View, ca: ConsentAction) {
            spClient?.onAction(view, ActionType.MSG_CANCEL)
        }

        override fun onDefaultAction(view: View, ca: ConsentAction) {
        }
    }
}