package com.sourcepoint.gdpr_cmplibrary.v6.builder

import com.sourcepoint.gdpr_cmplibrary.GDPRConsentLib


class BuilderV6 {

    fun setParam1() = apply { }
    fun setParam2() = apply { }
    fun setParam3() = apply { }

    fun <T : GDPRConsentLib> build(gdprConsentLibClientFirstClass: Class<T>): T {
        return when (gdprConsentLibClientFirstClass) {
            GDPRConsentLibClientFirst::class.java -> createFirst() as T
            else -> createSecond() as T
        }
    }


}

//@kotlin.Deprecated(message = "JUST FOR JAVA", level = DeprecationLevel.HIDDEN)
inline fun <reified T : GDPRConsentLib> createLegislationObj(): T {
    return when (T::class.java) {
        GDPRConsentLibClientFirst::class.java -> createFirst() as T
        else -> createSecond() as T
    }
}

fun main() {
    val first: GDPRConsentLibClientFirst = createLegislationObj()
    val second = createLegislationObj<GDPRConsentLibClientSecond>()
}