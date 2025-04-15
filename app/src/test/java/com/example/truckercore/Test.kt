package com.example.truckercore

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class Test {

    private lateinit var docParams: DocumentParameters

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }
    }

    @Test
    fun test() {

      

    }


}
