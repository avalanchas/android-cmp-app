package com.sourcepointmeta.metaapp

import com.example.uitestutil.*
import com.sourcepointmeta.metaapp.TestData.*


class MetaAppTestCases {

    companion object {

        fun tapOnAddProperty() {
            performClickById(R.id.action_addProperty)
        }

        fun addPropertyWithAllFields() {
            Utility.addPropertyWith(ALL_FIELDS)
        }

        fun addPropertyNoAccountId() {
            Utility.addPropertyWith(NO_ACCOUNT_ID)
        }

        fun addPropertyNoPropertyId() {
            Utility.addPropertyWith(NO_PROPERTY_ID)
        }

        fun addPropertyNoPropertyName() {
            Utility.addPropertyWith(NO_PROPERTY_NAME)
        }

        fun addPropertyNoPmId() {
            Utility.addPropertyWith(NO_PM_ID)
        }

        fun addPropertyWithAllFieldsBlank() {
            Utility.addPropertyWith(ALL_FIELDS_BLANK)
        }

        fun addPropertyWrongPrivacyManager() {
            Utility.addPropertyWith(WRONG_PRIVACY_MANAGER)
        }

        fun addPropertyWrongAccountId() {
            Utility.addPropertyWith(WRONG_ACCOUNT_ID)
        }

        fun addPropertyWrongPropertyName() {
            Utility.addPropertyWith(WRONG_PROPERTY_NAME)
        }

        fun addPropertyNoParamKey() {
            Utility.addPropertyWith(NO_PARAMETER_KEY)
        }

        fun addPropertyNoParamKeyValue() {
            Utility.addPropertyWith(NO_PARAMETER_KEY_VALUE)
        }

        fun addPropertyNoParamValue() {
            Utility.addPropertyWith(NO_PARAMETER_VALUE)
        }

        fun addPropertyWrongCampaign() {
            Utility.addPropertyWith(WRONG_CAMPAIGN)
        }

        fun tapDismissWebView() {
            performClickOnWebViewByClass("message-stacksclose")
        }

        fun tapOnSave() {
            performClickById(resId = R.id.action_saveProperty)
        }

        fun tapOkPopupError() {
            isDisplayedByResIdByText(R.id.message, MANDATORY_FIELDS)
            performClickByText("OK")
        }

        fun tapOkPopupErrorUnableLoadPm() {
            isDisplayedByResIdByText(R.id.message, UNABLE_TO_LOAD_PM_ERROR)
            performClickByText("OK")
        }

        fun tapOkPopupErrorPropertyExist() {
            isDisplayedByResIdByText(R.id.message, PROPERTY_EXITS_ERROR)
            performClickByText("OK")
        }

        fun tapOkPopupErrorParameter() {
            isDisplayedByResIdByText(R.id.message, TARGETING_PARAMETER_FIELDS)
            performClickByText("OK")
        }

        fun addNativeMessagePropertyDetails() {
            val accountId = "22"
            val propertyId = "7094"
            val propertyName = "tcfv2.mobile.demo"
            val pmId = "179657"

            insertTextByResId(propId = R.id.etAccountID, text = accountId)
            insertTextByResId(propId = R.id.etPropertyId, text = propertyId)
            insertTextByResId(propId = R.id.etPropertyName, text = propertyName)
            insertTextByResId(propId = R.id.etPMId, text = pmId)
            performClickById(resId = R.id.toggleNativeMessage)
            performSpinnerItemSelection(resId = R.id.spinner_message_language, contentDescription = ENGLISH)
        }

        fun addPropertyDetails() {
            val accountId = Example_accountID
            val propertyId = Example_propertyID
            val propertyName = Example_propertyName
            val pmId = Example_pmID

            insertTextByResId(propId = R.id.etAccountID, text = accountId)
            insertTextByResId(propId = R.id.etPropertyId, text = propertyId)
            insertTextByResId(propId = R.id.etPropertyName, text = propertyName)
            insertTextByResId(propId = R.id.etPMId, text = pmId)
            performSpinnerItemSelection(resId = R.id.spinner_message_language, contentDescription = ENGLISH);
        }

        fun addMessageLanguagePropertyDetails() {
            val accountId = "22"
            val propertyId = "7639"
            val propertyName = "tcfv2.mobile.webview"
            val pmId = "122058"

            insertTextByResId(propId = R.id.etAccountID, text = accountId)
            insertTextByResId(propId = R.id.etPropertyId, text = propertyId)
            insertTextByResId(propId = R.id.etPropertyName, text = propertyName)
            insertTextByResId(propId = R.id.etPMId, text = pmId)
            val messageLanguage = FRENCH;
            performSpinnerItemSelection(resId = R.id.spinner_message_language, contentDescription = messageLanguage)
        }

        fun addPMTabFeaturesPropertyDetails(pmTab : String){
            val accountId = "22"
            val propertyId = "7639"
            val propertyName = "tcfv2.mobile.webview"
            val pmId = "122058"

            insertTextByResId(propId = R.id.etAccountID, text = accountId)
            insertTextByResId(propId = R.id.etPropertyId, text = propertyId)
            insertTextByResId(propId = R.id.etPropertyName, text = propertyName)
            insertTextByResId(propId = R.id.etPMId, text = pmId)
            val messageLanguage = ENGLISH;
            performSpinnerItemSelection(resId = R.id.spinner_message_language, contentDescription = messageLanguage)

            performSpinnerItemSelection(resId = R.id.spinner_pm_tab, contentDescription = pmTab)
        }

        fun checkNativeMessageDisplayed() {
            isDisplayedAllOf(R.id.Title)
        }

        fun tapShowOption() {
            performClickById(R.id.ShowOption)
        }

        fun tapRejectAll() {
            performClickById(R.id.RejectAll)
        }

        fun tapAcceptAll() {
            performClickById(R.id.AcceptAll)
        }

        fun checkWebViewDisplayedForPrivacyManager() {
            checkWebViewHasText(PRIVACY_MANAGER)
        }

        fun checkConsentListNotSelected() {
            CONSENT_LIST.forEach { consent ->
                checkConsentState(consent, false)
            }
        }

        fun checkWebViewDisplayedForMessage() {
            checkWebViewHasText(MESSAGE)
        }

        fun tapRejectAllOnWebView() {
            performClickOnWebViewByContent(PM_REJECT_ALL)
        }

        fun tapPmCancelOnWebView() {
            performClickOnWebViewByContent(PM_CANCEL)
        }

        fun tapOptionOnWebView() {
            performClickOnWebViewByContent(OPTIONS)
        }

        fun checkPMTabSelectedFeatures() {
            checkPMTabSelected(FEATURES)
        }

        fun checkPMTabSelectedOptions() {
            checkPMTabSelected(OPTIONS)
        }

        fun tapPMAcceptAllOnWebView() {
            performClickOnWebViewByContent(PM_ACCEPT_ALL)
        }

        fun tapAcceptAllOnWebView() {
            performClickOnWebViewByContent(ACCEPT_ALL)
        }

        fun tapAcceptOnWebView() {
            performClickOnWebViewByContent(ACCEPT)
        }

        fun tapAcceptAllFrenchOnWebView() {
            performClickOnWebViewByContent(ACCEPT_ALL_FRENCH)
        }

        fun tapRejectAllFrenchOnWebView() {
            performClickOnWebViewByContent(REJECT_ALL_FRENCH)
        }

        fun tapManagePreferencesOnWebView() {
            performClickOnWebViewByContent(MANAGE_PREFERENCES)
        }

        fun checkForConsentsAreDisplayed() {
            isDisplayedByResId(R.id.consentRecyclerView)
        }

        fun tapShowOptionOnWebView() {
            performClickOnWebViewByContent(TestData.OPTIONS)
        }

        fun tapSaveAndExitOnWebView() {
            performClickOnWebViewByContent(PM_SAVE_AND_EXIT)
        }

        fun checkForPropertyInfoScreen() {
            isDisplayedByResId(R.id.consentRecyclerView)
        }

        fun checkForPropertyInfoInList() {
            isDisplayedByResId(R.id.swipe_layout)
        }

        fun checkForConsentAreDisplayed() {
            isDisplayedByResId(R.id.consentRecyclerView)
        }

        fun checkForConsentAreNotDisplayed() {
            isDisplayedByResId(R.id.tv_consentsNotAvailable)
        }

        fun navigateBackToListView() {
            performClickContent("Navigate up")
        }

        fun tapOnProperty() {
            performClickById(R.id.item_view)
        }

        fun checkWebViewDoesNotDisplayTheMessage() {
            checkWebViewDoesNotHasText(MESSAGE)
        }

        fun loadPrivacyManagerDirect() {
            performClickById(R.id.action_showPM)
        }

        fun swipeAndChooseResetAction() {
            swipeAndChooseAction(R.id.reset_button, R.id.item_view, YES)
        }

        fun swipeAndChooseEditActionUniqueAuth() {
            swipeAndChooseActionEdit(R.id.edit_button, R.id.item_view)
            Utility.addAuthentication(UNIQUE_AUTHENTICATION)
            tapOnSave()
        }

        fun swipeAndChooseEditActionParamValue() {
            swipeAndChooseActionEdit(R.id.edit_button, R.id.item_view)
            Utility.addParameterWithAuthentication(keyParam, valueParamEnglish, NO_AUTHENTICATION)
            tapOnSave()
        }

        fun selectNativeMessageConsentList() {
            clickPMTabSelectedPurposes()
            NATIVE_MESSAGE_CONSENT_LIST.forEach { consent ->
                checkConsentWebView(consent)
            }
        }

        fun selectPartialConsentList() {
            clickPMTabSelectedPurposes()
            PARTIAL_CONSENT_LIST.forEach { consent ->
                checkConsentWebView(consent)
            }
        }

        fun selectConsentList() {
            CONSENT_LIST.forEach { consent ->
                checkConsentWebView(consent)
            }
        }

        fun checkInsertedProperty() {
            isDisplayedByResId(R.id.action_addProperty)
        }

        fun checkConsentNotDisplayed() {
            isDisplayedByResId(R.id.tv_consentsNotAvailable)
        }

        fun addPropertyFor(messageType: String, authentication: String) {
            Utility.addPropertyFor(messageType, authentication)
        }

        fun checkPMTabSelectedPurposes() {
            checkPMTabSelected(PURPOSES)
        }

        fun clickPMTabSelectedPurposes() {
            performClickPMTabSelected(PURPOSES)
        }
    }
}