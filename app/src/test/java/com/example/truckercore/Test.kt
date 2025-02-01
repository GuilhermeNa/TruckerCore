package com.example.truckercore

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.utils.parameters.LicensingAggregation
import com.example.truckercore.shared.utils.parameters.ParametersBuilder
import com.example.truckercore.shared.utils.parameters.QueryData
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class Test {

    val user = TestUserDataProvider.getBaseEntity()

    companion object{
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }
    }

    @Test
    fun testSomeFun() {

        val param =
            ParametersBuilder(user)
                .setAggregation(LicensingAggregation(licensing = true))
                .setId("123")
                .setQueries(
                    QueryData(Field.ID, ""),
                    QueryData(Field.PARENT_ID, "")
                ).build()

        param.user
    }

}