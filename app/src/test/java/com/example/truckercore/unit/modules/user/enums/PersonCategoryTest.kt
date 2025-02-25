package com.example.truckercore.unit.modules.user.enums

import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.shared.errors.InvalidEnumParameterException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFalse

class PersonCategoryTest {

    @Test
    fun `should convert string to PersonCategory when valid`() {
        assertEquals(PersonCategory.ADMIN, PersonCategory.convertString("ADMIN"))
        assertEquals(PersonCategory.DRIVER, PersonCategory.convertString("DRIVER"))
    }

    @Test
    fun `should throw InvalidEnumParameterException when string is invalid`() {
        assertThrows<InvalidEnumParameterException> {
            PersonCategory.convertString("INVALID")
        }
    }

    @Test
    fun `should throw NullPointerException when string is null`() {
        assertThrows<NullPointerException> {
            PersonCategory.convertString(null)
        }
    }

    @Test
    fun `should return true for existing enum value`() {
        assertTrue(PersonCategory.enumExists("ADMIN"))
        assertTrue(PersonCategory.enumExists("DRIVER"))
    }

    @Test
    fun `should return false for non-existing enum value`() {
        assertFalse(PersonCategory.enumExists("INVALID"))
    }

}