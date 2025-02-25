package com.example.truckercore.unit.modules.employee.shared.enums

import com.example.truckercore.modules.employee.shared.enums.EmployeeStatus
import com.example.truckercore.shared.errors.InvalidEnumParameterException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EmployeeStatusTest {

    @Test
    fun testConvertString_ValidEnum() {
        val workingStatus = EmployeeStatus.convertString("WORKING")
        val vacationStatus = EmployeeStatus.convertString("VACATION")

        assertEquals(EmployeeStatus.WORKING, workingStatus)
        assertEquals(EmployeeStatus.VACATION, vacationStatus)
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