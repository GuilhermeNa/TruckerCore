package com.example.truckercore.unit.model.shared.errors.validation

import com.example.truckercore.modules.person.employee.driver.entity.Driver
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.validation.IllegalValidationArgumentException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.reflect.KClass

class IllegalValidationArgumentExceptionTest {

    @Test
    fun `should return correct message for IllegalValidationArgumentException`() {
        // Arrange
        val expected: KClass<*> = Driver::class  // Classe esperada
        val received: KClass<*> = User::class    // Classe recebida

        // Act
        val exception = assertThrows<IllegalValidationArgumentException> {
            throw IllegalValidationArgumentException(expected, received)
        }

        // Assert
        val expectedMessage = "Expected a Driver for validation but received: User."
        assertEquals(expectedMessage, exception.message)
    }

}