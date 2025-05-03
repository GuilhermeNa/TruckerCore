package com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors

import com.example.truckercore.model.errors.ErrorFactory
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InterpreterException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InvalidDataException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.MappingException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.NetworkException
import com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors.error_codes.DataFindErrCode
import com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors.error_codes.DataFlowErrCode

/**
 * Factory responsible for mapping data layer exceptions into domain-specific error codes ([DataErrorCode]).
 *
 * Each function transforms a raw [Throwable] into a structured error with:
 * - A specific error code ([DataErrorCode])
 * - A user-friendly message
 * - A developer-friendly log message
 *
 * The returned error objects are used consistently throughout the application
 * to ensure unified error handling in UI and logging systems.
 */
class DataAppErrorFactory : ErrorFactory {

    /**
     * Handles exceptions thrown during data retrieval operations (e.g., using a specification to fetch data).
     *
     * Maps known exceptions to specific [DataFindErrCode] values.
     *
     * @param e Exception thrown during the find operation.
     * @return A [DataAppException] with the mapped [DataFindErrCode].
     *
     * Possible error codes:
     * - [DataFindErrCode.Interpretation] – When the specification could not be interpreted
     * - [DataFindErrCode.InvalidData] – When the returned data is invalid or unusable
     * - [DataFindErrCode.Mapping] – When there is an issue converting raw data to domain models
     * - [DataFindErrCode.Network] – When a network-related error occurs during data fetching
     * - [DataFindErrCode.Unknown] – When an unexpected or unknown error occurs
     */
    fun handleFindError(e: Throwable): DataAppException {
        val code = when (e) {
            is InterpreterException -> DataFindErrCode.Interpretation
            is InvalidDataException -> DataFindErrCode.InvalidData
            is MappingException -> DataFindErrCode.Mapping
            is NetworkException -> DataFindErrCode.Network
            else -> DataFindErrCode.Unknown
        }

        factoryLogger(code)

        return DataAppException(
            message = code.logMessage,
            cause = e,
            errorCode = code
        )
    }

    /**
     * Handles exceptions thrown while maintaining or observing an active data flow (e.g., streams or reactive pipelines).
     *
     * Maps known flow-related exceptions to specific [DataFlowErrCode] values.
     *
     * @param e Exception thrown during the data flow.
     * @return A [DataAppException] with the mapped [DataFlowErrCode].
     *
     * Possible error codes:
     * - [DataFlowErrCode.Interpretation] – When the flow setup or request could not be interpreted
     * - [DataFlowErrCode.InvalidData] – When the flow emits invalid or corrupted data
     * - [DataFlowErrCode.Mapping] – When emitted data fails to map into the expected model
     * - [DataFlowErrCode.Network] – When the flow fails due to network issues
     * - [DataFlowErrCode.Unknown] – When an unexpected or unclassified error occurs
     */
    fun handleFlowError(e: Throwable): DataAppException {
        val code = when (e) {
            is InterpreterException -> DataFlowErrCode.Interpretation
            is InvalidDataException -> DataFlowErrCode.InvalidData
            is MappingException -> DataFlowErrCode.Mapping
            is NetworkException -> DataFlowErrCode.Network
            else -> DataFlowErrCode.Unknown
        }

        factoryLogger(code)

        return DataAppException(
            message = code.logMessage,
            cause = e,
            errorCode = code
        )
    }

}