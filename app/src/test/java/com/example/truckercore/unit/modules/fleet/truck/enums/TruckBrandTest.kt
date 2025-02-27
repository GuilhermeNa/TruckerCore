package com.example.truckercore.unit.modules.fleet.truck.enums

import com.example.truckercore.modules.fleet.truck.enums.TruckBrand
import com.example.truckercore.shared.errors.InvalidEnumParameterException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TruckBrandTest {

    private val expectedEnums = listOf(TruckBrand.SCANIA, TruckBrand.VOLVO)

    @Test
    fun `should have the expected enums`() {
        // Arrange
        val actualEnums = TruckBrand.entries.toTypedArray()

        // Act && Assert
        actualEnums.forEach { enum ->
            assertTrue(expectedEnums.contains(enum))
        }
    }

    @Test
    fun `should have the expected size`() {
        // Arrange
        val expectedSize = expectedEnums.size
        val actualSize = TruckBrand.entries.size

        // Assert
        assertEquals(expectedSize, actualSize)
    }

    @Test
    fun testConvertStringValidEnum() {
        val validString1 = "SCANIA"
        val validString2 = "VOLVO"

        // Testing valid strings
        assertEquals(TruckBrand.SCANIA, TruckBrand.convertString(validString1))
        assertEquals(TruckBrand.VOLVO, TruckBrand.convertString(validString2))
    }

    @Test
    fun testConvertStringInvalidEnum() {
        val invalidString = "MERCEDES"

        // Test for invalid string
        assertThrows<InvalidEnumParameterException> {
            TruckBrand.convertString(invalidString)
        }
    }

    @Test
    fun testConvertStringNull() {
        val nullString: String? = null

        // Test for null string
        assertThrows<NullPointerException> {
            TruckBrand.convertString(nullString)
        }
    }

    @Test
    fun testEnumExistsValid() {
        val validString1 = "SCANIA"
        val validString2 = "VOLVO"

        assertTrue(TruckBrand.enumExists(validString1))
        assertTrue(TruckBrand.enumExists(validString2))
    }

    @Test
    fun testEnumExistsInvalid() {
        val invalidString = "MERCEDES"
        assertFalse(TruckBrand.enumExists(invalidString))
    }

}