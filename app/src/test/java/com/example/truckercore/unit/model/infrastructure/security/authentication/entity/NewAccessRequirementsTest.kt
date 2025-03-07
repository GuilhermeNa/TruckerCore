package com.example.truckercore.unit.model.infrastructure.security.authentication.entity

import com.example.truckercore.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.shared.errors.InvalidStateException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class NewAccessRequirementsTest {

    @Test
    fun `should create object and not throw exception`() {
        val requirements = NewAccessRequirements(
            uid = " 123 ",
            name = " jo hn ",
            surname = " DOE ",
            email = " john.DOE@mail.com ",
            personFlag = PersonCategory.ADMIN
        )

        assertEquals("123", requirements.uid)
        assertEquals("John", requirements.name)
        assertEquals("Doe", requirements.surname)
        assertEquals("john.doe@mail.com", requirements.email)
        assertEquals(PersonCategory.ADMIN, requirements.personFlag)
    }

    @Test
    fun `should throw exception if UID is blank`() {
        assertThrows<InvalidStateException> {
            NewAccessRequirements(
                uid = "",  // Blank UID
                name = "John",
                surname = "Doe",
                email = "john.doe@mail.com",
                personFlag = PersonCategory.ADMIN
            )
        }
    }

    @Test
    fun `should throw exception if name contains numbers`() {
        assertThrows<InvalidStateException> {
            NewAccessRequirements(
                uid = "123",
                name = "John123",  // Invalid name with numbers
                surname = "Doe",
                email = "john.doe@mail.com",
                personFlag = PersonCategory.ADMIN
            )
        }
    }

    @Test
    fun `should throw exception if name contains invalid chars`() {
        assertThrows<InvalidStateException> {
            NewAccessRequirements(
                uid = "123",
                name = "John!",  // Invalid name with "!"
                surname = "Doe",
                email = "john.doe@mail.com",
                personFlag = PersonCategory.ADMIN
            )
        }
    }

    @Test
    fun `should throw exception if surname contains numbers`() {
        assertThrows<InvalidStateException> {
            NewAccessRequirements(
                uid = "123",
                name = "John",
                surname = "Doe1", // Invalid surname with number
                email = "john.doe@mail.com",
                personFlag = PersonCategory.ADMIN
            )
        }
    }

    @Test
    fun `should throw exception if surname contains invalid chars`() {
        assertThrows<InvalidStateException> {
            NewAccessRequirements(
                uid = "123",
                name = "John",
                surname = "Doe!", // Invalid surname with "!"
                email = "john.doe@mail.com",
                personFlag = PersonCategory.ADMIN
            )
        }
    }

    @Test
    fun `should throw exception if email is invalid`() {
        assertThrows<InvalidStateException> {
            NewAccessRequirements(
                uid = "123",
                name = "John",
                surname = "Doe",
                email = "invalidemail",  // Invalid email format
                personFlag = PersonCategory.ADMIN
            )
        }
    }

}