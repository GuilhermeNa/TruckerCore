package com.example.truckercore.unit.model.infrastructure.security.permissions.enums

import com.example.truckercore.model.infrastructure.security.data.enums.Permission
import com.example.truckercore.model.shared.errors.InvalidEnumParameterException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PermissionTest {

    @Test
    fun `should convert string to valid Permission`() {
        // Test with valid string values
        val permissionViewLicensing = Permission.convertString("VIEW_LICENSING")
        assertEquals(Permission.VIEW_LICENSING, permissionViewLicensing)

        val permissionCreateLicensing = Permission.convertString("CREATE_LICENSING")
        assertEquals(Permission.CREATE_LICENSING, permissionCreateLicensing)

        val permissionUpdateLicensing = Permission.convertString("UPDATE_LICENSING")
        assertEquals(Permission.UPDATE_LICENSING, permissionUpdateLicensing)

        val permissionDeleteLicensing = Permission.convertString("DELETE_LICENSING")
        assertEquals(Permission.DELETE_LICENSING, permissionDeleteLicensing)

        val permissionViewTrailer = Permission.convertString("VIEW_TRAILER")
        assertEquals(Permission.VIEW_TRAILER, permissionViewTrailer)

        val permissionViewUser = Permission.convertString("VIEW_USER")
        assertEquals(Permission.VIEW_USER, permissionViewUser)
    }

    @Test
    fun `should throw InvalidEnumParameterException for invalid string`() {
        // Test with an invalid string
        val invalidString = "INVALID_PERMISSION"
        assertThrows<InvalidEnumParameterException> {
            Permission.convertString(invalidString)
        }
    }

    @Test
    fun `should throw NullPointerException for null string`() {
        // Test with a null string
        assertThrows<NullPointerException> {
            Permission.convertString(null)
        }
    }

    @Test
    fun `should return true for existing enum name in enumExists`() {
        // Test with an existing enum name
        val exists = Permission.enumExists("VIEW_LICENSING")
        assertTrue(exists)
    }

    @Test
    fun `should return false for non-existing enum name in enumExists`() {
        // Test with a non-existing enum name
        val exists = Permission.enumExists("NON_EXISTING_PERMISSION")
        assertTrue(!exists)
    }

}