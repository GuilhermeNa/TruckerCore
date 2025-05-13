package com.example.truckercore.model.infrastructure.integration.auth.for_app.repository

import com.example.truckercore.model.errors.exceptions.InfraException
import com.example.truckercore.model.errors.exceptions.TechnicalException
import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.AuthSourceException
import com.example.truckercore.model.infrastructure.integration.auth.for_api.exceptions.NetworkException


/**
 * Factory responsible for converting [AuthSourceException] instances into [AuthenticationAppException].
 *
 * This class acts as a centralized error transformation layer between the authentication domain and
 * the application layer. It maps domain-specific exceptions from the authentication module to
 * application-specific exceptions, each carrying a semantic [AppErrorCode] that the presentation layer can handle.
 *
 * Each method corresponds to a specific authentication operation and uses exhaustive `when` branches to
 * ensure appropriate error code assignment for each error type.
 *
 * ### Example usage:
 * ```
 * try {
 *     authSource.createUserWithEmail(email, password)
 * } catch (e: AuthSourceException) {
 *     val appError = appErrorFactory.creatingUserWithEmail(e)
 * }
 * ```
 */
object AuthErrorFactory {

    operator fun invoke(message: String, cause: Exception): AppResult.Error {
        val error = when (cause) {
            is NetworkException -> InfraException.NetworkUnavailable()
            is AuthSourceException -> InfraException.AuthError(message, cause)
            else -> TechnicalException.Unknown(message, cause)
        }
        return AppResult.Error(error)
    }

}


