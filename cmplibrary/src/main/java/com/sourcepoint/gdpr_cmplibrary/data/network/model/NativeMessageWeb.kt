package com.sourcepoint.gdpr_cmplibrary.data.network.model

data class NativeMessageWeb(
    val actions: List<Action>,
    val addtlConsent: String,
    val bucket: Int,
    val categoryId: Int,
    val customVendorsResponse: CustomVendorsResponse,
    val euconsent: String,
    val gdprApplies: Boolean,
    val grants: String,
    val messageId: Int,
    val msgDescription: String,
    val propertyId: Int,
    val prtnUUID: String,
    val stackInfo: StackInfo,
    val subCategoryId: Int,
    val uuid: String,
    val msgJSON: String
)