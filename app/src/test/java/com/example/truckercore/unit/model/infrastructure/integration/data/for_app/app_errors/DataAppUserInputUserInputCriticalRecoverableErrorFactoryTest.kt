package com.example.truckercore.unit.model.infrastructure.integration.data.for_app.app_errors

import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InterpreterException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InvalidDataException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.MappingException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.NetworkException
import com.example.truckercore.model.infrastructure.integration.data.for_app.repository.DataRepositoryErrorFactory
import com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors.error_codes.DataFindErrCode
import com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors.error_codes.DataFlowErrCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DataAppUserInputUserInputCriticalRecoverableErrorFactoryTest {

    private val factory = DataRepositoryErrorFactory()

    //----------------------------------------------------------------------------------------------
    // Testing handleFindError
    //----------------------------------------------------------------------------------------------
    @Test
    fun `handleFindError should map InterpreterException to InterpretationError`() {
        // Arrange
        val ex = InterpreterException("Error interpreting data")

        // Act
        val result = factory.handleFindError(ex)

        // Assert
        assertEquals(DataFindErrCode.Interpretation, result.errorCode)
        assertEquals(DataFindErrCode.Interpretation.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `handleFindError should map InvalidDataException to InvalidData`() {
        // Arrange
        val ex = InvalidDataException("Received invalid data")

        // Act
        val result = factory.handleFindError(ex)

        // Assert
        assertEquals(DataFindErrCode.InvalidData, result.errorCode)
        assertEquals(DataFindErrCode.InvalidData.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `handleFindError should map MappingException to MappingError`() {
        // Arrange
        val ex = MappingException("Error mapping data")

        // Act
        val result = factory.handleFindError(ex)

        // Assert
        assertEquals(DataFindErrCode.Mapping, result.errorCode)
        assertEquals(DataFindErrCode.Mapping.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `handleFindError should map NetworkException to NetworkError`() {
        // Arrange
        val ex = NetworkException("Network error occurred")

        // Act
        val result = factory.handleFindError(ex)

        // Assert
        assertEquals(DataFindErrCode.Network, result.errorCode)
        assertEquals(DataFindErrCode.Network.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `handleFindError should map unknown exception to UnknownError`() {
        // Arrange
        val ex = RuntimeException("Unexpected error")

        // Act
        val result = factory.handleFindError(ex)

        // Assert
        assertEquals(DataFindErrCode.Unknown, result.errorCode)
        assertEquals(DataFindErrCode.Unknown.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    //----------------------------------------------------------------------------------------------
    // Testing handleFlowError
    //----------------------------------------------------------------------------------------------
    @Test
    fun `handleFlowError should map InterpreterException to InterpretationError`() {
        // Arrange
        val ex = InterpreterException("Error interpreting flow data")

        // Act
        val result = factory.handleFlowError(ex)

        // Assert
        assertEquals(DataFlowErrCode.Interpretation, result.errorCode)
        assertEquals(DataFlowErrCode.Interpretation.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `handleFlowError should map InvalidDataException to InvalidData`() {
        // Arrange
        val ex = InvalidDataException("Flow emitted invalid data")

        // Act
        val result = factory.handleFlowError(ex)

        // Assert
        assertEquals(DataFlowErrCode.InvalidData, result.errorCode)
        assertEquals(DataFlowErrCode.InvalidData.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `handleFlowError should map MappingException to MappingError`() {
        // Arrange
        val ex = MappingException("Error mapping flow data")

        // Act
        val result = factory.handleFlowError(ex)

        // Assert
        assertEquals(DataFlowErrCode.Mapping, result.errorCode)
        assertEquals(DataFlowErrCode.Mapping.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `handleFlowError should map NetworkException to NetworkError`() {
        // Arrange
        val ex = NetworkException("Network failure during flow operation")

        // Act
        val result = factory.handleFlowError(ex)

        // Assert
        assertEquals(DataFlowErrCode.Network, result.errorCode)
        assertEquals(DataFlowErrCode.Network.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `handleFlowError should map unknown exception to UnknownError`() {
        // Arrange
        val ex = RuntimeException("Unexpected flow error")

        // Act
        val result = factory.handleFlowError(ex)

        // Assert
        assertEquals(DataFlowErrCode.Unknown, result.errorCode)
        assertEquals(DataFlowErrCode.Unknown.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

}