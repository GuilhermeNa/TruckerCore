package com.example.truckercore.unit.modules.fleet.trailer.enums

import com.example.truckercore.modules.fleet.trailer.enums.TrailerBrand
import com.example.truckercore.shared.errors.InvalidEnumParameterException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFailsWith

class TrailerBrandTest {

    @Test
    fun `should convert string to TrailerBrand successfully`() {
        // Arrange
        val brandStr = "LIBRELATO"

        // Act
        val result = TrailerBrand.convertString(brandStr)

        // Assert
        assertEquals(TrailerBrand.LIBRELATO, result)
    }

    @Test
    fun `should throw exception for invalid string when converting to TrailerBrand`() {
        // Arrange
        val invalidBrandStr = "UNKNOWN_BRAND"

        // Act & Assert
        assertThrows<InvalidEnumParameterException> {
            TrailerBrand.convertString(invalidBrandStr)
        }
    }

    @Test
    fun `should return true for an existing TrailerBrand enum when checking if it exists`() {
        // Arrange
        val validBrand = "RANDOM"

        // Act
        val result = TrailerBrand.enumExists(validBrand)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `should return false for a non-existing TrailerBrand enum when checking if it exists`() {
        // Arrange
        val invalidBrand = "NON_EXISTING_BRAND"

        // Act
        val result = TrailerBrand.enumExists(invalidBrand)

        // Assert
        assertTrue(!result)
    }

    @Test
    fun `should throw NullPointerException for null string when converting to TrailerBrand`() {
        // Act & Assert
        assertFailsWith<NullPointerException> {
            TrailerBrand.convertString(null)
        }
    }

}