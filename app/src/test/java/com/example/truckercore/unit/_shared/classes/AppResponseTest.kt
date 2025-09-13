package com.example.truckercore.unit._shared.classes

import com.example.truckercore._shared.classes.AppResponse
import com.example.truckercore._shared.classes.map
import com.example.truckercore._shared.classes.onEmpty
import com.example.truckercore._shared.classes.onFailure
import com.example.truckercore._shared.classes.onSuccess
import com.example.truckercore._shared.classes.onSuccessUnit
import com.example.truckercore.model.errors.AppException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AppResponseTest {

    @Test
    fun `should create a success response and return correct data`() {
        // Arrange
        val expectedInstance = AppResponse.Success::class.java
        val expectedData = "value"

        // Act
        val response = AppResponse.success(expectedData)

        // Assert
        assertInstanceOf(expectedInstance, response)
        assertEquals(expectedData, response.data)
    }

    @Test
    fun `should create a successUnit response`() {
        // Arrange
        val expectedInstance = AppResponse.SuccessUnit::class.java

        // Act
        val response = AppResponse.successUnit

        // Assert
        assertInstanceOf(expectedInstance, response)
    }

    @Test
    fun `should create an empty response`() {
        // Arrange
        val expectedInstance = AppResponse.Empty::class.java

        // Act
        val response = AppResponse.empty

        // Assert
        assertInstanceOf(expectedInstance, response)
    }

    @Test
    fun `should create a failure response and return correct exception`() {
        // Arrange
        val expectedInstance = AppResponse.Failure::class.java
        val expectedException = AppException("Test exception.")

        // Act
        val response = AppResponse.failure(expectedException)

        // Assert
        assertInstanceOf(expectedInstance, response)
        assertEquals(expectedException, response.exception)
    }

    @Test
    fun `onSuccess should execute action when response is Success`() {
        // Arrange
        val expectedData = "value"
        val response = AppResponse.success(expectedData)
        var result: String? = null

        // Act
        response.onSuccess { data ->
            result = data
        }

        // Assert
        assertEquals(expectedData, result)
    }

    @Test
    fun `onSuccess should return the same instance`() {
        // Arrange
        val expectedObject = AppResponse.success("data")

        // Act
        val result = expectedObject.onSuccess {}

        // Assert
        assertEquals(expectedObject, result)
    }

    @Test
    fun `onFailure should execute action when response is Failure`() {
        // Arrange
        val expectedException = AppException("Test exception.")
        val response = AppResponse.failure(expectedException)
        var result: AppException? = null

        // Act
        response.onFailure { exception ->
            result = exception
        }

        // Assert
        assertEquals(expectedException, result)
    }

    @Test
    fun `onFailure should return the same instance`() {
        // Arrange
        val expectedObject = AppResponse.failure(AppException("Test failure."))

        // Act
        val result = expectedObject.onFailure {}

        // Assert
        assertEquals(expectedObject, result)
    }

    @Test
    fun `onEmpty should execute action when response is Empty`() {
        // Arrange
        val response = AppResponse.empty
        var wasCalled = false

        // Act
        response.onEmpty {
            wasCalled = true
        }

        // Assert
        assertTrue(wasCalled)
    }

    @Test
    fun `onEmpty should return the same instance`() {
        // Arrange
        val expectedObject = AppResponse.empty

        // Act
        val result = expectedObject.onEmpty {}

        // Assert
        assertEquals(expectedObject, result)
    }

    @Test
    fun `onSuccessUnit should execute action when response is SuccessUnit`() {
        // Arrange
        val response = AppResponse.successUnit
        var wasCalled = false

        // Act
        response.onSuccessUnit {
            wasCalled = true
        }

        // Assert
        assertTrue(wasCalled)
    }

    @Test
    fun `onSuccessUnit should return the same instance`() {
        // Arrange
        val expectedObject = AppResponse.successUnit

        // Act
        val result = expectedObject.onSuccessUnit {}

        // Assert
        assertEquals(expectedObject, result)
    }

    // TODO( Finalizar os testes do mapeamento )

    @Test
    fun `map should return value from onSuccess when response is Success`() {
        // Arrange
        val expectedData = "Mapped: Kotlin"
        val response = AppResponse.success("Kotlin")

        // Act
        val result = response.map(
            onSuccess = { "Mapped: $it" },
            onSuccessUnit = { "Mapped: Unit" },
            onEmpty = { "Mapped: Empty" },
            onError = { "Mapped: Error ${it.message}" }
        )

        // Assert
        assertEquals(expectedData, result)
    }

    @Test
    fun `map should return value from onSuccessUnit when response is SuccessUnit`() {
        // Arrange
        val expected = "Mapped: Unit"
        val response = AppResponse.successUnit

        // Act
        val result = response.map(
            onSuccess = { "Mapped: $it" },
            onSuccessUnit = { expected },
            onEmpty = { "Mapped: Empty" },
            onError = { "Mapped: Error ${it.message}" }
        )

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun `map should return value from onEmpty when response is Empty`() {
        // Arrange
        val expected = "Mapped: Empty"
        val response = AppResponse.empty

        // Act
        val result = response.map(
            onSuccess = { "Mapped: $it" },
            onSuccessUnit = { "Mapped: Unit" },
            onEmpty = { expected },
            onError = { "Mapped: Error ${it.message}" }
        )

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun `map should return value from onError when response is Failure`() {
        // Arrange
        val exception = AppException("Network failure")
        val response = AppResponse.failure(exception)
        val expected = "Mapped: Error Network failure"

        // Act
        val result = response.map(
            onSuccess = { "Mapped: $it" },
            onSuccessUnit = { "Mapped: Unit" },
            onEmpty = { "Mapped: Empty" },
            onError = { "Mapped: Error ${it.message}" }
        )

        // Assert
        assertEquals(expected, result)
    }

}