package com.sourcepoint.gdpr_cmplibrary.data.network

import com.beust.klaxon.Klaxon
import com.sourcepoint.gdpr_cmplibrary.TestUtilGson
import com.sourcepoint.gdpr_cmplibrary.assertNotNull
import com.sourcepoint.gdpr_cmplibrary.data.network.model.NativeMessageWeb
import org.junit.Test

class DataModelTest{

    @Test
    fun `convert json`(){
        val s = TestUtilGson.run { "native-message-web.json".jsonFile2String() }
            .assertNotNull()

        val result = Klaxon()
            .parse<NativeMessageWeb>(s).assertNotNull()

        println(result)

    }

}