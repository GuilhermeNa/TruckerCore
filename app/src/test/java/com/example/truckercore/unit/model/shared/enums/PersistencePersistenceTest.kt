package com.example.truckercore.unit.model.shared.enums

import com.example.truckercore.model.shared.errors.InvalidEnumParameterException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PersistencePersistenceTest {

    private val expectedEnums = arrayOf(
        PersistenceStatus.PENDING, PersistenceStatus.PERSISTED, PersistenceStatus.ARCHIVED
    )

    @Test
    fun `expected enums should exists`() {
        // Assertions
        expectedEnums.forEach { query ->
            assertTrue(PersistenceStatus.entries.toTypedArray().contains(query))
        }
    }

    @Test
    fun `should have the exactly number of enums`() {
        // Arrange
        val expectedSize = expectedEnums.size

        // Assertions
        kotlin.test.assertEquals(expectedSize, PersistenceStatus.entries.size)
    }

    @Test
    fun `convertString should return correct PersistenceStatus for valid string`() {
        assertEquals(
            PersistenceStatus.PENDING,
            PersistenceStatus.convertString("PENDING")
        )

        assertEquals(
            PersistenceStatus.PERSISTED,
            PersistenceStatus.convertString("PERSISTED")
        )

        assertEquals(
            PersistenceStatus.ARCHIVED,
            PersistenceStatus.convertString("ARCHIVED")
        )
    }

    @Test
    fun `convertString should throw InvalidEnumParameterException for invalid string`() {
        assertThrows<InvalidEnumParameterException> {
            PersistenceStatus.convertString("INVALID")
        }
    }

    @Test
    fun `enumExists should return true for valid enum`() {
        assertTrue(PersistenceStatus.enumExists("PENDING"))
        assertTrue(PersistenceStatus.enumExists("PERSISTED"))
        assertTrue(PersistenceStatus.enumExists("ARCHIVED"))
    }

    @Test
    fun `enumExists should return false for invalid enum`() {
        assertFalse(PersistenceStatus.enumExists("INVALID"))
        assertFalse(PersistenceStatus.enumExists("NON_EXISTENT"))
    }

    @Test
    fun `convertString should throw NullPointerException for null string`() {
        assertThrows<NullPointerException> {
            PersistenceStatus.convertString(null)
        }
    }

}