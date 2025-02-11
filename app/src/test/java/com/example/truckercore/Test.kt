package com.example.truckercore

import com.example.truckercore._test_data_provider.TestDriverDataProvider
import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.admin.aggregations.AdminWithDetails
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.AggregateAdminWithDetails
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.first
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class Test {

    private lateinit var aggregUseCase: AggregateAdminWithDetails
    private lateinit var docParams : DocumentParameters

    val t = AdminWithDetails(
        TestDriverDataProvider.getBaseEntity(),
        TestStorageFileDataProvider.getBaseEntity(),
        emptySet()
    )

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }
    }

    @Test
    fun testar() {
        val a = aggregUseCase.execute<Admin>(docParams)
        val b = a.first()

        (b as Response.Success).data.employee
    }









}
