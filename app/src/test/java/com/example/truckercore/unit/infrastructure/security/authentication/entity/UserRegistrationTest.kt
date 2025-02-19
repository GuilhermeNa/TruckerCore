package com.example.truckercore.unit.infrastructure.security.authentication.entity

import com.example.truckercore.infrastructure.security.authentication.entity.UserRegistration
import com.example.truckercore.shared.errors.InvalidStateException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserRegistrationTest {

    @Test
    fun `should create user with valid details`() {
        // Call
        val userReg = UserRegistration(
            "Guilherme",
            "Silva",
            "guilherme@example.com",
            "123456"
        )

        // Assert
        assertEquals("Guilherme", userReg.name)
        assertEquals("Silva", userReg.surname)
        assertEquals("guilherme@example.com", userReg.email)
        assertEquals("123456", userReg.password)
        assertEquals("Guilherme Silva", userReg.getCompleteName())
    }

    @Test
    fun `should trim all received params`() {
        // Call
        val userReg =
            UserRegistration(" Guil herme ", " Sil va ", " guil herme@example.com ", " 123 456 ")

        // Assert
        assertEquals("Guilherme", userReg.name)
        assertEquals("Silva", userReg.surname)
        assertEquals("guilherme@example.com", userReg.email)
        assertEquals("123456", userReg.password)
        assertEquals("Guilherme Silva", userReg.getCompleteName())
    }

    @Test
    fun `should capitalize correctly the params`() {
        // Call
        val userReg = UserRegistration("guilHerme", "sILVA", "GUILHERME@example.com", "123456")

        // Assert
        assertEquals("Guilherme", userReg.name)
        assertEquals("Silva", userReg.surname)
        assertEquals("guilherme@example.com", userReg.email)
        assertEquals("123456", userReg.password)
        assertEquals("Guilherme Silva", userReg.getCompleteName())
    }

    @Test
    fun `should throw InvalidStateException for name with number chars`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            UserRegistration("Guilherme1", "Silva", "guilherme@example.com", "123456")
        }
    }

    @Test
    fun `should throw InvalidStateException for name with special chars`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            UserRegistration(
                "Guilherme!",
                "Silva",
                "guilherme@example.com",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidStateException for surname with number chars`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            UserRegistration(
                "Guilherme",
                "Silva1",
                "guilherme@example.com",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidStateException for surname with special chars`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            UserRegistration(
                "Guilherme",
                "Silva!",
                "guilherme@example.com",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidStateException for email without dot`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            UserRegistration(
                "Guilherme",
                "Silva",
                "guilherme@example",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidStateException for email without @`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            UserRegistration(
                "Guilherme",
                "Silva",
                "guilherme.com",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidStateException for email without name`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            UserRegistration(
                "Guilherme",
                "Silva",
                "@sample.com",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidStateException for password with less than 6 chars`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            UserRegistration(
                "Guilherme",
                "Silva",
                "guilherme@example.com",
                "12345"
            )
        }
    }

}