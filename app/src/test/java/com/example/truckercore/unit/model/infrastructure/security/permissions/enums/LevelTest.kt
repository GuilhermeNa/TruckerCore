/*
package com.example.truckercore.unit.model.infrastructure.security.permissions.enums

import com.example.truckercore.model.shared.errors.InvalidEnumParameterException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import kotlin.test.assertFailsWith

class LevelTest {

    @Test
    fun `should convert string to valid Level`() {
        // Test with valid string values
        val levelMaster = Level.convertString("MASTER")
        assertEquals(Level.MASTER, levelMaster)

        val levelManager = Level.convertString("MANAGER")
        assertEquals(Level.MANAGER, levelManager)

        val levelModerator = Level.convertString("MODERATOR")
        assertEquals(Level.MODERATOR, levelModerator)

        val levelDriver = Level.convertString("OPERATIONAL")
        assertEquals(Level.OPERATIONAL, levelDriver)
    }

    @Test
    fun `should throw InvalidEnumParameterException for invalid string`() {
        // Test with an invalid string
        val invalidString = "INVALID_LEVEL"
        assertThrows<InvalidEnumParameterException> {
            Level.convertString(invalidString)
        }

    }

    @Test
    fun `should throw NullPointerException for null string`() {
        // Test with a null string
         assertFailsWith<NullPointerException> {
            Level.convertString(null)
        }
    }

    @Test
    fun `should return true for existing enum name in enumExists`() {
        // Test with an existing enum name
        val exists = Level.enumExists("MASTER")
        assertTrue(exists)
    }

    @Test
    fun `should return false for non-existing enum name in enumExists`() {
        // Test with a non-existing enum name
        val exists = Level.enumExists("NON_EXISTING_LEVEL")
        assertTrue(!exists)
    }

}*/
