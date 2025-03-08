package com.example.truckercore.unit.model.modules.person.employee.shared.enums

import com.example.truckercore.model.modules.person.employee.shared.enums.EmployeeStatus
import com.example.truckercore.model.shared.errors.InvalidEnumParameterException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EmployeeStatusTest {

    private val expectedEnums = hashSetOf(
        EmployeeStatus.WORKING, EmployeeStatus.VACATION, EmployeeStatus.ON_LEAVE
    )

    @Test
    fun `should have the expected enums`() {
        // Arrange
        val actualEnums = EmployeeStatus.entries.toHashSet()

        // Act && Assert
        actualEnums.forEach { enum ->
            assertTrue(expectedEnums.contains(enum))
        }

    }

    @Test
    fun `should have the expected size of enums`() {
        // Arrange
        val expectedSize = expectedEnums.size
        val actualSize = EmployeeStatus.entries.size

        // Assertion
        assertEquals(expectedSize, actualSize)
    }

    @Test
    fun testConvertString_ValidEnum() {
        // Act && Assert
        assertEquals(
            EmployeeStatus.WORKING,
            EmployeeStatus.convertString("WORKING")
        )
        assertEquals(
            EmployeeStatus.VACATION,
            EmployeeStatus.convertString("VACATION")
        )
    }

    @Test
    fun testConvertString_InvalidEnum() {
        assertThrows<InvalidEnumParameterException> {
            EmployeeStatus.convertString("INVALID_STATUS")
        }
    }

    @Test
    fun testConvertString_NullEnum() {
        assertThrows<NullPointerException> {
            EmployeeStatus.convertString(null)
        }
    }

    @Test
    fun testEnumExists_ValidEnum() {
        val exists = EmployeeStatus.enumExists("VACATION")
        assertTrue(exists)
    }

    @Test
    fun testEnumExists_InvalidEnum() {
        val exists = EmployeeStatus.enumExists("NOT_EXISTING")
        assertFalse(exists)
    }

}