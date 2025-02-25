package com.example.truckercore.unit.shared.enums

import com.example.truckercore.shared.enums.QueryType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QueryTypeTest {

    private val expectedEnums = arrayOf(
        QueryType.WHERE_IN, QueryType.WHERE_EQUALS
    )

    @Test
    fun `expected enums should exists`() {
        // Assertions
        expectedEnums.forEach { query ->
            assertTrue(QueryType.entries.toTypedArray().contains(query))
        }
    }

    @Test
    fun `should have the exactly number of enums`() {
        // Arrange
        val expectedSize = expectedEnums.size

        // Assertions
        assertEquals(expectedSize, QueryType.entries.size)
    }

}