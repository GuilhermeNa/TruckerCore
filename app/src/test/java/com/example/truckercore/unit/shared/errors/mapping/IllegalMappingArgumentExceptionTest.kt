package com.example.truckercore.unit.shared.errors.mapping

import com.example.truckercore.modules.person.employee.admin.dto.AdminDto
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.modules.file.dto.FileDto
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

class IllegalMappingArgumentExceptionTest {

    @Test
    fun `should return correct message for IllegalMappingArgumentException`() {
        // Arrange
        val receivedDto: FileDto = mockk()

        // Act
        val exception = assertThrows<IllegalMappingArgumentException> {
            throw IllegalMappingArgumentException(
                AdminDto::class.simpleName,
                receivedDto::class.simpleName
            )
        }

        // Assert
        assertTrue(exception.message.contains(AdminDto::class.simpleName.toString()))
        assertTrue(exception.message.contains(receivedDto::class.simpleName.toString()))
    }

}