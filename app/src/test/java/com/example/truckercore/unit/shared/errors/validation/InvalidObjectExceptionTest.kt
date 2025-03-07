package com.example.truckercore.unit.shared.errors.validation

import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.validation.InvalidObjectException
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertNull

class InvalidObjectExceptionTest {

    @Test
    fun `should generate correct message for Entity`() {
        // Arrange
        val entity: User = mockk()
        val invalidFields = listOf("field1", "field2")

        // Act
        val exception = assertThrows<InvalidObjectException> {
            throw InvalidObjectException(entity, invalidFields)
        }

        // Assert
        val expectedMessage = "Some error occurred while validating an object:" +
                " [User] -> invalid field [field1, field2]."
        assertEquals(expectedMessage, exception.message)
        assertEquals(entity, exception.entity)
        assertNull(exception.dto)
    }

    @Test
    fun `should generate correct message for Dto`() {
        // Arrange
        val dto: UserDto = mockk()
        val invalidFields = listOf("field1", "field2")

        // Act
        val exception = assertThrows<InvalidObjectException> {
            throw InvalidObjectException(dto, invalidFields)
        }

        // Assert
        val expectedMessage = "Some error occurred while validating an object:" +
                " [UserDto] -> invalid field [field1, field2]."
        assertEquals(expectedMessage, exception.message)
        assertEquals(dto, exception.dto)
        assertNull(exception.entity)
    }

}