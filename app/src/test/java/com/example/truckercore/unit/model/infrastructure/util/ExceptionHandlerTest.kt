package com.example.truckercore.unit.model.infrastructure.util

import android.util.Log
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.util.ExceptionHandler
import com.example.truckercore.shared.errors.UnknownErrorException
import io.mockk.verify
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

    @Test
    fun `should log exception message in handleException`() {
        // Arrange
        val message = "Test exception message"
        val exception = Exception(message)

        // Act
        exceptionHandler.run(exception)

        // Assert
        verify { Log.e(any(), message) }
    }

    @Test
    fun `should log error message in handleException`() {
        // Arrange
        val message = "Test error message"
        val exception = Throwable(message)

        // Act
        exceptionHandler.run(exception)

        // Assert
        verify { Log.e(any(), message) }
    }

}