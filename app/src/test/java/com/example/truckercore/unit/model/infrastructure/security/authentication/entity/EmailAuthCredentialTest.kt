package com.example.truckercore.unit.model.infrastructure.security.authentication.entity

import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.errors.InvalidEmailException
import com.example.truckercore.model.infrastructure.security.authentication.errors.InvalidPasswordException
import com.example.truckercore.model.infrastructure.security.authentication.expressions.toHash
import com.example.truckercore.view_model.errors.EmptyUserNameException
import com.example.truckercore.view_model.errors.IncompleteNameException
import com.example.truckercore.view_model.errors.InvalidSizeException
import com.example.truckercore.view_model.errors.WrongNameFormatException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EmailAuthCredentialTest {

    @Test
    fun `should create user with valid details`() {
        // Call
        val userReg = EmailAuthCredential(
            "John Doe",
            "guilherme@example.com",
            "123456"
        )

        // Assert
        assertEquals("John Doe", userReg.name)
        assertEquals("guilherme@example.com", userReg.email)
        assertEquals("123456".toHash(), userReg.password)
    }

    @Test
    fun `should trim all received params`() {
        // Call
        val userReg =
            EmailAuthCredential(
                " John Doe ",
                " guil herme@example.com ",
                " 123 456 "
            )

        // Assert
        assertEquals("John Doe", userReg.name)
        assertEquals("guilherme@example.com", userReg.email)
        assertEquals("123456".toHash(), userReg.password)
    }

    @Test
    fun `should capitalize correctly the params`() {
        // Call
        val userReg = EmailAuthCredential(
            "JOHN doe",
            "GUILHERME@example.com",
            "123456"
        )

        // Assert
        assertEquals("John Doe", userReg.name)
        assertEquals("guilherme@example.com", userReg.email)
        assertEquals("123456".toHash(), userReg.password)
    }

    @Test
    fun `should throw InvalidEmailException for email without dot`() {
        // Call && Assert
        assertThrows<InvalidEmailException> {
            EmailAuthCredential(
                "John Doe",
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
                "John Doe",
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
                "John Doe",
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
                "John Doe",
                "guilherme@example.com",
                "12345"
            )
        }
    }

    @Test
    fun `should throw EmptyUserNameException for name empty`() {
        // Call && Assert
        assertThrows<EmptyUserNameException> {
            EmailAuthCredential(
                "",
                "guilherme@example.com",
                "12345"
            )
        }
    }

    @Test
    fun `should throw InvalidSizeException for name lower than 5`() {
        // Call && Assert
        assertThrows<InvalidSizeException> {
            EmailAuthCredential(
                "Ab",
                "guilherme@example.com",
                "12345"
            )
        }
    }

    @Test
    fun `should throw IncompleteNameException for name with only one word`() {
        // Call && Assert
        assertThrows<IncompleteNameException> {
            EmailAuthCredential(
                "Joaquin",
                "guilherme@example.com",
                "12345"
            )
        }
    }

    @Test
    fun `should throw WrongNameFormatException for name with number`() {
        // Call && Assert
        assertThrows<WrongNameFormatException> {
            EmailAuthCredential(
                "Jhon Doe1",
                "guilherme@example.com",
                "12345"
            )
        }
    }

    @Test
    fun `should throw WrongNameFormatException for name with special char`() {
        // Call && Assert
        assertThrows<WrongNameFormatException> {
            EmailAuthCredential(
                "Jhon Doe!",
                "guilherme@example.com",
                "12345"
            )
        }
    }

}