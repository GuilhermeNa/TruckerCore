package com.example.truckercore.unit.shared.utils

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/*
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
        val validQuery = QuerySettings(Field.NAME, "John Doe")
        val liveObserver = true

        // Call
        val queryParameters = QueryParameters.create(user)
            .setQueries(arrayOf(validQuery))
            .setLiveObserver(liveObserver)
            .build()

        // Assert
        assertEquals(user, queryParameters.user)
        assertEquals(liveObserver, queryParameters.liveObserver)
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
    fun `should throw IllegalArgumentException when no queries are provided`() {
        // Arrange
        val liveObserver = true

        // Call & Assert
        val exception = assertThrows<IllegalArgumentException> {
            QueryParameters.create(user)
                .setLiveObserver(liveObserver)
                .build()
        }

        // Assert
        assertEquals(
            QueryParameters.EMPTY_QUERY_ERROR,
            exception.message
        )
    }

    @Test
    fun `should throw IllegalArgumentException when queries contain ID field`() {
        // Arrange
        val queryWithId =
            QuerySettings(Field.ID, "idValue")
        val liveObserver = true

        // Call
        val exception = assertThrows<IllegalArgumentException> {
            QueryParameters.create(user)
                .setQueries(arrayOf(queryWithId))
                .setLiveObserver(liveObserver)
                .build()
        }

        // Assertions
        assertEquals(
            QueryParameters.ID_ERROR,
            exception.message
        )
    }

    @Test
    fun `should set liveObserver to false in QueryParameters when nothing is passed`() {
        // Arrange
        val validQuery = QuerySettings(Field.NAME, "John Doe")

        // Call
        val queryParameters = QueryParameters.create(user)
            .setQueries(arrayOf(validQuery))
            .build()

        // Assert
        assertFalse(queryParameters.liveObserver)
    }

}*/
