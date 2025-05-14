package com.example.truckercore.model.infrastructure.integration.data.for_app.app_errors

import com.example.truckercore.model.errors.ErrorFactory
import com.example.truckercore.model.errors.exceptions.infra.InfraException
import com.example.truckercore.model.errors.exceptions.technical.TechnicalException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.NetworkException
import com.example.truckercore._utils.classes.AppResponse

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

    operator fun invoke(message: String, cause: Throwable): AppResponse.Error {
        val error = when (cause) {
            is NetworkException -> InfraException.NetworkUnavailable()
            is DataAppException -> InfraException.DatabaseError(message, cause)
            else -> TechnicalException.Unknown(message, cause)
        }
        return AppResponse.Error(error)
    }

}