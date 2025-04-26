package com.example.truckercore.unit.model.infrastructure.integration.data.for_app.app_errors.error_codes

import com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors.error_codes.DataFlowErrCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class DataFlowErrCodeTest {

    @ParameterizedTest(name = "Code: {0}")
    @MethodSource("provideErrorCodes")
    fun `each DataFlowErrCode should have correct properties`(
        errorCode: DataFlowErrCode,
        expectedName: String,
        expectedUserMessage: String,
        expectedLogMessage: String,
        expectedIsRecoverable: Boolean
    ) {
        // Act & Assert
        assertEquals(expectedName, errorCode.name)
        assertEquals(expectedUserMessage, errorCode.userMessage)
        assertEquals(expectedLogMessage, errorCode.logMessage)
        assertEquals(expectedIsRecoverable, errorCode.isRecoverable)
    }

    companion object {
        @JvmStatic
        fun provideErrorCodes() = listOf(
            Arguments.of(
                DataFlowErrCode.Interpretation,
                "INTERPRETATION_ERROR",
                "Erro ao interpretar requisição de dados.",
                "Failed to interpret data during retrieval.",
                false
            ),
            Arguments.of(
                DataFlowErrCode.InvalidData,
                "INVALID_DATA",
                "Os dados recebidos são inválidos.",
                "Invalid data found during retrieval process.",
                false
            ),
            Arguments.of(
                DataFlowErrCode.Mapping,
                "MAPPING_ERROR",
                "Erro ao converter os dados.",
                "Failed to map data to domain model during retrieval.",
                false
            ),
            Arguments.of(
                DataFlowErrCode.Network,
                "NETWORK_ERROR",
                "Erro de rede. Verifique sua conexão.",
                "Network failure during data fetch operation.",
                true
            ),
            Arguments.of(
                DataFlowErrCode.Unknown,
                "UNKNOWN_ERROR",
                "Erro desconhecido. Tente novamente.",
                "Unexpected error during data retrieval.",
                false
            )
        )
    }

}