package com.example.truckercore.unit.shared.utils

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.errors.InvalidQuerySettingsException
import com.example.truckercore.shared.utils.parameters.QuerySettings
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QuerySettingsTest {

    private val validField = Field.NAME
    private val invalidField = Field.ID

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }
    }

    @Test
    fun `should create QuerySettings successfully when valid parameters are provided`() {
        // Arrange
        val type = QueryType.WHERE_EQUALS
        val value = "Test Value"

        // Call
        val querySettings = QuerySettings(validField, type, value)

        // Assertions
        assertNotNull(querySettings)
        assertEquals(validField, querySettings.field)
        assertEquals(type, querySettings.type)
        assertEquals(value, querySettings.value)
    }

    @Test
    fun `should throw InvalidQuerySettingsException when ID field is used`() {
        val type = QueryType.WHERE_EQUALS
        val value = "Test Value"

        // Assert
        assertThrows<InvalidQuerySettingsException> {
            QuerySettings(invalidField, type, value)
        }
    }

    @Test
    fun `should throw InvalidQuerySettingsException when WHERE_IN type is used with non-list value`() {
        val type = QueryType.WHERE_IN
        val wrongValue = "Test Value"

        // Assert
        assertThrows<InvalidQuerySettingsException> {
            QuerySettings(validField, type, wrongValue)
        }
    }

    @Test
    fun `should throw InvalidQuerySettingsException when WHERE_EQUALS type is used with a list value`() {
        val type = QueryType.WHERE_EQUALS
        val value = listOf("Test Value 1", "Test Value 2")

        // Assert
        assertThrows<InvalidQuerySettingsException> {
            QuerySettings(validField, type, value)
        }
    }

}