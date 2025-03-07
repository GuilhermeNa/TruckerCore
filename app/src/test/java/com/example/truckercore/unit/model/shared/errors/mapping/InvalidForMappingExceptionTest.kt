package com.example.truckercore.unit.model.shared.errors.mapping

import com.example.truckercore.modules.person.employee.admin.dto.AdminDto
import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertNull
import kotlin.test.assertTrue

class InvalidForMappingExceptionTest {

    @Test
    fun `should return correct message for DTO mapping error`() {
        // Arrange
        val dto: AdminDto = mockk() // Usamos mockk para um mock de Dto
        val cause = Exception("Underlying exception")

        // Act
        val exception = assertThrows<InvalidForMappingException> {
            throw InvalidForMappingException(dto, cause)
        }

        // Assert
        assertTrue(exception.message.contains(dto.toString()))
        assertEquals(dto, exception.getObject())
        assertEquals(cause, exception.exception)
        assertEquals(dto, exception.dto)
        assertNull(exception.entity)
    }

    @Test
    fun `should return correct message for Entity mapping error`() {
        // Arrange
        val entity: Admin = mockk()
        val cause = Exception("Underlying exception")

        // Act
        val exception = assertThrows<InvalidForMappingException> {
            throw InvalidForMappingException(entity, cause)
        }

        // Assert
        assertTrue(exception.message.contains(entity.toString()))
        assertEquals(entity, exception.getObject())
        assertEquals(cause, exception.exception)
        assertEquals(entity, exception.entity)
        assertNull(exception.dto)
    }

}