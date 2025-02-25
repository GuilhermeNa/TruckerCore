package com.example.truckercore.unit.shared.utils.parameters

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.errors.search_params.IllegalQueryParametersException
import com.example.truckercore.shared.errors.InvalidQuerySettingsException
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QueryParametersTest {

    private val user = TestUserDataProvider.getBaseEntity()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }
    }

    @Test
    fun `should build a valid QueryParameters when valid queries are provided`() {
        // Arrange
        val validQuery = QuerySettings(Field.NAME, QueryType.WHERE_EQUALS, "John Doe")

        // Call
        val queryParameters = QueryParameters.create(user)
            .setQueries(validQuery)
            .setStream(true)
            .build()

        // Assert
        assertEquals(user, queryParameters.user)
        assertTrue(queryParameters.shouldStream)
        assertEquals(1, queryParameters.queries.size)
        assertEquals(
            Field.NAME,
            queryParameters.queries.first().field
        )
        assertEquals(
            "John Doe",
            queryParameters.queries.first().value
        )
    }

    @Test
    fun `should throw IllegalQueryParametersException when no queries are provided`() {
        assertThrows<IllegalQueryParametersException> {
            QueryParameters.create(user)
                .setStream(true)
                .build()
        }
    }

    @Test
    fun `should throw InvalidQuerySettingsException when queries contain ID field`() {
        assertThrows<InvalidQuerySettingsException> {
            QueryParameters.create(user)
                .setQueries(QuerySettings(Field.ID, QueryType.WHERE_EQUALS, "idValue"))
                .setStream(true)
                .build()
        }
    }

    @Test
    fun `should set liveObserver to false in QueryParameters when nothing is passed`() {
        // Arrange
        val validQuery = QuerySettings(Field.NAME, QueryType.WHERE_EQUALS, "John Doe")

        // Call
        val queryParameters = QueryParameters.create(user)
            .setQueries(validQuery)
            .build()

        // Assert
        assertFalse(queryParameters.shouldStream)
    }

}

