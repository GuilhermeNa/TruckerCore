package com.example.truckercore.unit.model.infrastructure.integration.writer.for_app.app_errors

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions.InstructionNotImplementedException
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions.InvalidInstructionException
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.app_errors.ExecutorAppErrorFactory
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.app_errors.error_codes.ExecuteInstructionErrCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExecutorAppUserInputUserInputErrorFactoryTest {

    private val factory = ExecutorAppErrorFactory()

    @Test
    fun `should map InstructionNotImplementedException to InstructionNotImplemented`() {
        // Arrange
        val ex = InstructionNotImplementedException()

        // Act
        val result = factory(ex)

        // Assert
        assertEquals(ExecuteInstructionErrCode.InstructionNotImplemented, result.errorCode)
        assertEquals(ExecuteInstructionErrCode.InstructionNotImplemented.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `should map InvalidInstructionException to InvalidInstruction`() {
        // Arrange
        val ex = InvalidInstructionException()

        // Act
        val result = factory(ex)

        // Assert
        assertEquals(ExecuteInstructionErrCode.InvalidInstruction, result.errorCode)
        assertEquals(ExecuteInstructionErrCode.InvalidInstruction.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

    @Test
    fun `should map unknown exception to Unknown`() {
        // Arrange
        val ex = RuntimeException("Some unknown error")

        // Act
        val result = factory(ex)

        // Assert
        assertEquals(ExecuteInstructionErrCode.Unknown, result.errorCode)
        assertEquals(ExecuteInstructionErrCode.Unknown.logMessage, result.message)
        assertEquals(ex, result.cause)
    }

}