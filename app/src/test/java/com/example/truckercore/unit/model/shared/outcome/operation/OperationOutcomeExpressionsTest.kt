package com.example.truckercore.unit.model.shared.outcome.operation

import com.example.truckercore.layers.data.base.outcome.expressions.handle
import com.example.truckercore.layers.data.base.outcome.expressions.map
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OperationOutcomeExpressionsTest {

    //----------------------------------------------------------------------------------------------
    // map()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `map should return value from onComplete when response is Positive`() {
        // Arrange
        val expectedResult = "Map: Complete"
        val response = OperationOutcome.Completed

        // Act
        val result = response.map(
            onComplete = { expectedResult },
            onFailure = { "Map: Failure" }
        )

        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `map should return value from onFailure when response is Failure`() {
        // Arrange
        val expectedResult = "Map: Failure"
        val response = OperationOutcome.Failure(
            com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException(
                "Test exception"
            )
        )

        // Act
        val result = response.map(
            onComplete = { "Map: Positive" },
            onFailure = { expectedResult }
        )

        // Assert
        assertEquals(expectedResult, result)
    }

    //----------------------------------------------------------------------------------------------
    // handle()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `handle should execute onComplete when outcome is Completed`() {
        // Arrange
        var wasOnCompleteCalled = false
        var wasOnFailureCalled = false
        val response = OperationOutcome.Completed

        // Act
        response.handle(
            onComplete = { wasOnCompleteCalled = true },
            onFailure = { wasOnFailureCalled = true }
        )

        // Assert
        assertTrue(wasOnCompleteCalled)
        assertFalse(wasOnFailureCalled)
    }

    @Test
    fun `handle should execute onFailure when outcome is Failure`() {
        // Arrange
        var wasOnCompleteCalled = false
        var wasOnFailureCalled = false
        val exception =
            com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException("Test failure")
        val response = OperationOutcome.Failure(exception)

        // Act
        response.handle(
            onComplete = { wasOnCompleteCalled = true },
            onFailure = { wasOnFailureCalled = true }
        )

        // Assert
        assertFalse(wasOnCompleteCalled)
        assertTrue(wasOnFailureCalled)
    }

    @Test
    fun `handle should pass the correct exception to onFailure`() {
        // Arrange
        var capturedException: com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException? = null
        val exception =
            com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException("Specific failure")
        val response = OperationOutcome.Failure(exception)

        // Act
        response.handle(
            onComplete = { /* no-op */ },
            onFailure = { capturedException = it }
        )

        // Assert
        assertEquals(exception, capturedException)
    }

}