package com.example.truckercore.model.infrastructure.integration.data.for_app.app_exception.error_codes

import com.example.truckercore.model.infrastructure.integration.data.for_app.app_exception.DataErrorCode
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification

/**
 * Sealed class representing error codes related to maintaining an active data flow.
 *
 * Implementations of this sealed class provide:
 * - `name`: A unique identifier for the error.
 * - `userMessage`: A user-friendly message that can be shown to the end user (in Portuguese).
 * - `logMessage`: A message that will be logged for internal tracking and debugging.
 * - `isRecoverable`: A boolean indicating if the error is recoverable (i.e., whether the operation can be retried).
 */
sealed class DataFlowErrCode : DataErrorCode {

    /**
     * Error indicating that the [Specification] could not be interpreted correctly.
     */
    data object Interpretation : DataFlowErrCode() {
        override val name = "INTERPRETATION_ERROR"
        override val userMessage = "Erro ao interpretar requisição de dados."
        override val logMessage = "Failed to interpret data during retrieval."
        override val isRecoverable = false
    }

    /**
     * Error indicating that the retrieved data is invalid or does not match expected criteria.
     */
    data object InvalidData : DataFlowErrCode() {
        override val name = "INVALID_DATA"
        override val userMessage = "Os dados recebidos são inválidos."
        override val logMessage = "Invalid data found during retrieval process."
        override val isRecoverable = false
    }

    /**
     * Error indicating a failure during mapping of retrieved data to domain models.
     */
    data object Mapping : DataFlowErrCode() {
        override val name = "MAPPING_ERROR"
        override val userMessage = "Erro ao converter os dados."
        override val logMessage = "Failed to map data to domain model during retrieval."
        override val isRecoverable = false
    }

    /**
     * Error indicating a network issue occurred while attempting to retrieve data.
     */
    data object Network : DataFlowErrCode() {
        override val name = "NETWORK_ERROR"
        override val userMessage = "Erro de rede. Verifique sua conexão."
        override val logMessage = "Network failure during data fetch operation."
        override val isRecoverable = true
    }

    /**
     * Error indicating an unknown failure occurred during the data retrieval process.
     */
    data object Unknown : DataFlowErrCode() {
        override val name = "UNKNOWN_ERROR"
        override val userMessage = "Erro desconhecido. Tente novamente."
        override val logMessage = "Unexpected error during data retrieval."
        override val isRecoverable = false
    }

}