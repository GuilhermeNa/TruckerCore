package com.example.truckercore.unit.model.shared.outcome.data

import com.example.truckercore.data.shared.outcome.InvalidOutcomeException
import com.example.truckercore.data.shared.outcome.data.DataOutcome
import com.example.truckercore.data.shared.outcome.data.EMPTY_DATA_ERROR_MESSAGE
import com.example.truckercore.data.shared.outcome.data.FAILURE_DATA_ERROR_MESSAGE
import com.example.truckercore.data.shared.outcome.data.getOrNull
import com.example.truckercore.data.shared.outcome.data.getRequired
import com.example.truckercore.data.shared.outcome.data.handle
import com.example.truckercore.data.shared.outcome.data.map
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DataOutcomeExpressionsTest {

    //----------------------------------------------------------------------------------------------
    // map()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `map should return value from onSuccess when response is Success`() {
        // Arrange
        val expectedResult = "Map: Success"
        val response = DataOutcome.Success("")

        // Act
        val result = response.map(
            onSuccess = { expectedResult },
            onEmpty = { "Map: Empty" },
            onFailure = { "Map: Error" }
        )

        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `map should return value from onEmpty when response is Empty`() {
        // Arrange
        val expectedResult = "Map: Empty"
        val response = DataOutcome.Empty

        // Act
        val result = response.map(
            onSuccess = { "Map: Success" },
            onEmpty = { expectedResult },
            onFailure = { "Map: Error" }
        )

        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `map should return value from onFailure when response is Failure`() {
        // Arrange
        val expectedResult = "Map: Failure"
        val response = DataOutcome.Failure(
            com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException(
                "Test exception"
            )
        )

        // Act
        val result = response.map(
            onSuccess = { "Map: Success" },
            onEmpty = { "Map: Empty" },
            onFailure = { expectedResult }
        )

        // Assert
        assertEquals(expectedResult, result)
    }

    //----------------------------------------------------------------------------------------------
    // getRequired()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `getRequired should return value when response is Success`() {
        // Arrange
        val expectedResult = "Map: Success"
        val outcome = DataOutcome.Success(expectedResult)

        // Act
        val result = outcome.getRequired()

        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getRequired should throw InvalidOutcomeException when response is Empty`() {
        // Arrange
        val outcome = DataOutcome.Empty

        // Act && Assert
        val exception = assertThrows<InvalidOutcomeException> {
            outcome.getRequired()
        }

        assertEquals(exception.message, EMPTY_DATA_ERROR_MESSAGE)
    }

    @Test
    fun `getRequired should throw InvalidOutcomeException when response is Failure`() {
        // Arrange
        val cause =
            com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException("Test exception.")
        val outcome = DataOutcome.Failure(cause)

        // Act && Assert
        val exception = assertThrows<InvalidOutcomeException> {
            outcome.getRequired()
        }

        assertEquals(exception.message, FAILURE_DATA_ERROR_MESSAGE)
        assertEquals(exception.cause, cause)
    }

    //----------------------------------------------------------------------------------------------
    // getOrNull()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `getOrNull should return value when response is Success`() {
        // Arrange
        val expectedResult = "Map: Success"
        val outcome = DataOutcome.Success(expectedResult)

        // Act
        val result = outcome.getOrNull()

        // Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getOrNull should return null when response is Empty`() {
        // Arrange
        val outcome: DataOutcome<String> = DataOutcome.Empty

        // Act
        val result = outcome.getOrNull()

        // Assert
        assertNull(result)
    }

    @Test
    fun `getOrNull should return null when response is Failure`() {
        // Arrange
        val outcome: DataOutcome<String> = DataOutcome.Failure(
            com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException(
                "Test exception"
            )
        )

        // Act
        val result = outcome.getOrNull()

        // Assert
        assertNull(result)
    }

    //----------------------------------------------------------------------------------------------
    // handle()
    //----------------------------------------------------------------------------------------------
    @Test
    fun `handle should execute onSuccess when outcome is Success`() {
        // Arrange
        var wasSuccessCalled = false
        var wasEmptyCalled = false
        var wasFailureCalled = false
        val data = "Success data"
        val outcome = DataOutcome.Success(data)

        // Act
        outcome.handle(
            onSuccess = {
                wasSuccessCalled = true
            },
            onEmpty = {
                wasEmptyCalled = true
            },
            onFailure = {
                wasFailureCalled = true
            }
        )

        // Assert
        assertTrue(wasSuccessCalled)
        assertFalse(wasEmptyCalled)
        assertFalse(wasFailureCalled)
    }

    @Test
    fun `handle should execute onEmpty when outcome is Empty`() {
        // Arrange
        var wasSuccessCalled = false
        var wasEmptyCalled = false
        var wasFailureCalled = false
        val outcome = DataOutcome.Empty

        // Act
        outcome.handle(
            onSuccess = {
                wasSuccessCalled = true
            },
            onEmpty = {
                wasEmptyCalled = true
            },
            onFailure = {
                wasFailureCalled = true
            }
        )

        // Assert
        assertFalse(wasSuccessCalled)
        assertTrue(wasEmptyCalled)
        assertFalse(wasFailureCalled)
    }

    @Test
    fun `handle should execute onFailure when outcome is Failure`() {
        // Arrange
        var wasSuccessCalled = false
        var wasEmptyCalled = false
        var wasFailureCalled = false
        val exception =
            com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException("Test exception")
        val outcome = DataOutcome.Failure(exception)

        // Act
        outcome.handle(
            onSuccess = {
                wasSuccessCalled = true
            },
            onEmpty = {
                wasEmptyCalled = true
            },
            onFailure = {
                wasFailureCalled = true
            }
        )

        // Assert
        assertFalse(wasSuccessCalled)
        assertFalse(wasEmptyCalled)
        assertTrue(wasFailureCalled)
    }

    @Test
    fun `handle should pass correct data to onSuccess`() {
        // Arrange
        var receivedData: String? = null
        val data = "Expected data"
        val outcome = DataOutcome.Success(data)

        // Act
        outcome.handle(
            onSuccess = { receivedData = it },
            onEmpty = { },
            onFailure = { }
        )

        // Assert
        assertEquals(data, receivedData)
    }

    @Test
    fun `handle should pass correct exception to onFailure`() {
        // Arrange
        var receivedException: com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException? = null
        val exception =
            com.example.truckercore.data.infrastructure.app_errors.abstractions.AppException("Expected failure")
        val outcome = DataOutcome.Failure(exception)

        // Act
        outcome.handle(
            onSuccess = { },
            onEmpty = { },
            onFailure = { receivedException = it }
        )

        // Assert
        assertEquals(exception, receivedException)
    }

}