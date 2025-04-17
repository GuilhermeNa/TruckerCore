package com.example.truckercore.unit.model.infrastructure.util

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.shared.errors.UnknownErrorException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExceptionHandlerTest {

    private val exceptionHandler = ExceptionHandler()

    @BeforeEach
    fun setup() {
        mockStaticLog()
    }

    @Test
    fun `should handle Exception and return Response Error`() {
        // Arrange
        val exception = Exception("Test exception message")

        // Act
        val result = exceptionHandler.run(exception)

        // Assert
        assertEquals(exception, result.exception)
    }

    @Test
    fun `should handle non-Exception throwable and return Response Error`() {
        // Arrange
        val throwable = Throwable("Test error message")

        // Act
        val result = exceptionHandler.run(throwable)

        // Assert
        assertTrue(result.exception is UnknownErrorException)
    }

}