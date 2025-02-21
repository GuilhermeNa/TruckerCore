package com.example.truckercore.unit.infrastructure.security.authentication.entity

import com.example.truckercore.infrastructure.security.authentication.entity.Credentials
import com.example.truckercore.shared.errors.InvalidStateException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CredentialsTest {

    @Test
    fun `should create user with valid details`() {
        // Call
        val userReg = Credentials(
            "guilherme@example.com",
            "123456"
        )

        // Assert
        assertEquals("guilherme@example.com", userReg.email)
        assertEquals("123456", userReg.password)
    }

    @Test
    fun `should trim all received params`() {
        // Call
        val userReg =
            Credentials(
                " guil herme@example.com ",
                " 123 456 "
            )

        // Assert
        assertEquals("guilherme@example.com", userReg.email)
        assertEquals("123456", userReg.password)
    }

    @Test
    fun `should capitalize correctly the params`() {
        // Call
        val userReg = Credentials(
            "GUILHERME@example.com",
            "123456"
        )

        // Assert
        assertEquals("guilherme@example.com", userReg.email)
        assertEquals("123456", userReg.password)
    }

    @Test
    fun `should throw InvalidStateException for email without dot`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            Credentials(
                "guilherme@example",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidStateException for email without @`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            Credentials(
                "guilherme.com",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidStateException for email without name`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            Credentials(
                "@sample.com",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidStateException for password with less than 6 chars`() {
        // Call && Assert
        assertThrows<InvalidStateException> {
            Credentials(
                "guilherme@example.com",
                "12345"
            )
        }
    }

}