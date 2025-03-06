package com.example.truckercore.unit.shared.utils.expressions

import com.example.truckercore.shared.utils.expressions.capitalizeFirstChar
import com.example.truckercore.shared.utils.expressions.isEmailFormat
import com.example.truckercore.shared.utils.expressions.isNameFormat
import com.example.truckercore.shared.utils.expressions.removeBlank
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StringExpressionsTest {

    @Test
    fun `should capitalize the first character and lowercase the rest`() {
        // Given a string
        val input = "hello world"

        // When applying capitalizeFirstChar
        val result = input.capitalizeFirstChar()

        // Then the first letter should be capitalized, and the rest should be lowercase
        assertEquals("Hello world", result)
    }

    @Test
    fun `should not change string that starts with an uppercase letter`() {
        // Given a string that starts with uppercase
        val input = "Hello World"

        // When applying capitalizeFirstChar
        val result = input.capitalizeFirstChar()

        // Then the result should be the same as the input
        assertEquals("Hello world", result)
    }

    @Test
    fun `should handle an empty string gracefully`() {
        // Given an empty string
        val input = ""

        // When applying capitalizeFirstChar
        val result = input.capitalizeFirstChar()

        // Then the result should still be an empty string
        assertEquals("", result)
    }

    @Test
    fun `should return true for a valid name format`() {
        // Given a valid name (alphabetic only)
        val input = "John"

        // When checking the name format
        val result = input.isNameFormat()

        // Then the result should be true
        assertTrue(result)
    }

    @Test
    fun `should return false for a name with non-alphabetic characters`() {
        // Given a name with non-alphabetic characters
        val input = "John123"

        // When checking the name format
        val result = input.isNameFormat()

        // Then the result should be false
        assertFalse(result)
    }

    @Test
    fun `should return false for a name with spaces`() {
        // Given a name with spaces
        val input = "John Doe"

        // When checking the name format
        val result = input.isNameFormat()

        // Then the result should be false
        assertFalse(result)
    }

    @Test
    fun `should return true for a valid email format`() {
        // Given a valid email
        val input = "john.doe@example.com"

        // When checking the email format
        val result = input.isEmailFormat()

        // Then the result should be true
        assertTrue(result)
    }

    @Test
    fun `should return false for an invalid email without @ symbol`() {
        // Given an invalid email without @ symbol
        val input = "johndoeexample.com"

        // When checking the email format
        val result = input.isEmailFormat()

        // Then the result should be false
        assertFalse(result)
    }

    @Test
    fun `should return false for an email with invalid domain`() {
        // Given an email with invalid domain
        val input = "john.doe@com"

        // When checking the email format
        val result = input.isEmailFormat()

        // Then the result should be false
        assertFalse(result)
    }

    @Test
    fun `should remove all spaces from a string`() {
        // Given a string with spaces
        val input = "Hello World"

        // When removing the spaces
        val result = input.removeBlank()

        // Then the result should be "HelloWorld"
        assertEquals("HelloWorld", result)
    }

    @Test
    fun `should return the same string if there are no spaces`() {
        // Given a string without spaces
        val input = "HelloWorld"

        // When removing spaces
        val result = input.removeBlank()

        // Then the result should be the same as the input
        assertEquals("HelloWorld", result)
    }

    @Test
    fun `should return an empty string when input is only spaces`() {
        // Given a string with only spaces
        val input = "    "

        // When removing spaces
        val result = input.removeBlank()

        // Then the result should be an empty string
        assertEquals("", result)
    }

}