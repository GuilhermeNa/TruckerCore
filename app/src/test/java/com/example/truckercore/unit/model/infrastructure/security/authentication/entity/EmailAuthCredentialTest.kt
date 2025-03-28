package com.example.truckercore.unit.model.infrastructure.security.authentication.entity

import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.errors.InvalidEmailException
import com.example.truckercore.model.infrastructure.security.authentication.errors.InvalidPasswordException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EmailAuthCredentialTest {

    @Test
    fun `should create user with valid details`() {
        // Call
        val userReg = EmailAuthCredential(
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
            EmailAuthCredential(
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
        val userReg = EmailAuthCredential(
            "GUILHERME@example.com",
            "123456"
        )

        // Assert
        assertEquals("guilherme@example.com", userReg.email)
        assertEquals("123456", userReg.password)
    }

    @Test
    fun `should throw InvalidEmailException for email without dot`() {
        // Call && Assert
        assertThrows<InvalidEmailException> {
            EmailAuthCredential(
                "guilherme@example",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidEmailException for email without @`() {
        // Call && Assert
        assertThrows<InvalidEmailException> {
            EmailAuthCredential(
                "guilherme.com",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidEmailException for email without name`() {
        // Call && Assert
        assertThrows<InvalidEmailException> {
            EmailAuthCredential(
                "@sample.com",
                "123456"
            )
        }
    }

    @Test
    fun `should throw InvalidPasswordException for password with less than 6 chars`() {
        // Call && Assert
        assertThrows<InvalidPasswordException> {
            EmailAuthCredential(
                "guilherme@example.com",
                "12345"
            )
        }
    }

}