package com.example.truckercore.unit.model.infrastructure.integration.writer.for_app.app_errors.error_codes

import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.app_errors.error_codes.ExecuteInstructionErrCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ExecuteInstructionErrCodeTest {

    @ParameterizedTest(name = "Code: {0}")
    @MethodSource("provideErrorCodes")
    fun `each ExecuteInstructionErrCode should have correct properties`(
        errorCode: ExecuteInstructionErrCode,
        expectedName: String,
        expectedUserMessage: String,
        expectedLogMessage: String,
        expectedIsRecoverable: Boolean
    ) {
        assertEquals(expectedName, errorCode.name)
        assertEquals(expectedUserMessage, errorCode.userMessage)
        assertEquals(expectedLogMessage, errorCode.logMessage)
        assertEquals(expectedIsRecoverable, errorCode.isRecoverable)
    }

    companion object {
        @JvmStatic
        fun provideErrorCodes() = listOf(
            Arguments.of(
                ExecuteInstructionErrCode.InvalidInstruction,
                "INVALID_INSTRUCTION",
                "As instruções recebidas são inválidas.",
                "Invalid instruction found during write process.",
                true
            ),
            Arguments.of(
                ExecuteInstructionErrCode.InstructionNotImplemented,
                "INSTRUCTION_NOT_IMPLEMENTED",
                "Instrução não reconhecida pelo servidor.",
                "The instruction is not implemented yet in back-end interpreter.",
                true
            ),
            Arguments.of(
                ExecuteInstructionErrCode.Unknown,
                "UNKNOWN_ERROR",
                "Erro desconhecido. Tente novamente.",
                "Unexpected error during data writing.",
                false
            )
        )
    }

}