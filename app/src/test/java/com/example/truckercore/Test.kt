package com.example.truckercore

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.AggregateAdminWithDetails
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class Test {

    private lateinit var aggregUseCase: AggregateAdminWithDetails
    private lateinit var docParams : DocumentParameters


    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }
    }

    @Test
    fun testar() {

    }









}
