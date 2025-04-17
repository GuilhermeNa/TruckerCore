package com.example.truckercore.unit.model.shared.utils.expressions

import com.example.truckercore.model.shared.utils.expressions.capitalizeEveryFirstChar
import com.example.truckercore.model.shared.utils.expressions.isEmailFormat
import com.example.truckercore.model.shared.utils.expressions.isNameFormat
import com.example.truckercore.model.shared.utils.expressions.removeBlank
import com.example.truckercore.model.shared.utils.expressions.formatAsFullName
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StringExpressionsTest {

    //----------------------------------------------------------------------------------------------
    // String.capitalizeEveryFirstChar()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should capitalize every first char and trim spaces`() {
        // Given a string
        val input = " hello world "

        // When applying capitalizeFirstChar
        val result = input.capitalizeEveryFirstChar()

        // Then the first letter should be capitalized, and the rest should be lowercase
        assertEquals(" Hello World ", result)
    }

    @Test
    fun `should set all string in lowercase and the first in uppercase `() {
        // Given a string that starts with uppercase
        val input = "HELLO WORLD"

        // When applying capitalizeFirstChar
        val result = input.capitalizeEveryFirstChar()

        // Then the result should be the same as the input
        assertEquals("Hello World", result)
    }

    @Test
    fun `should handle with empty string`() {
        // Given an empty string
        val input = ""

        // When applying capitalizeFirstChar
        val result = input.capitalizeEveryFirstChar()

        // Then the result should still be an empty string
        assertEquals("", result)
    }

    //----------------------------------------------------------------------------------------------
    // String.toCompleteNameFormat()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return in name format when its lowercase `() {
        // Arrange
        val name = "josé da silva"

        // Act
        val result = name.formatAsFullName()

        // Assert
        assertEquals(result, "José da Silva")
    }

    @Test
    fun `should return in name format when its uppercase `() {
        // Arrange
        val name = "JOSÉ DA SILVA"

        // Act
        val result = name.formatAsFullName()

        // Assert
        assertEquals(result, "José da Silva")
    }

    @Test
    fun `should return in name format when has blank space`() {
        // Arrange
        val name = " JOSÉ   DA SILVA  "

        // Act
        val result = name.formatAsFullName()

        // Assert
        assertEquals(result, "José da Silva")
    }

    //----------------------------------------------------------------------------------------------
    // String.isNameFormat()
    //----------------------------------------------------------------------------------------------
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
    fun `should return true for a valid name format with space`() {
        // Given a valid name (alphabetic only)
        val input = "John Doe"

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
        val input = "John Doe!"

        // When checking the name format
        val result = input.isNameFormat()

        // Then the result should be false
        assertFalse(result)
    }

    //----------------------------------------------------------------------------------------------
    // String.isEmailFormat()
    //----------------------------------------------------------------------------------------------
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

    //----------------------------------------------------------------------------------------------
    // String.removeBlank()
    //----------------------------------------------------------------------------------------------
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