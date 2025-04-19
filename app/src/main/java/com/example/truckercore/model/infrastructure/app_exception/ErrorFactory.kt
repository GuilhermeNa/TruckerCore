package com.example.truckercore.model.infrastructure.app_exception

import com.example.truckercore.model.shared.utils.expressions.logError

/**
 * Common methods for error factories
 */
interface ErrorFactory {

    /**
     * Logs error details to the logging system.
     *
     * @param code The [ErrorCode] that represents the error that occurred.
     *
     * ```kotlin
     * val errorCode = NewEmailErrCode.InvalidCredentials
     * factoryLogger(errorCode)
     * // Logs: [INVALID_CREDENTIALS] Invalid credentials provided during account creation.
     * ```
     */
    fun factoryLogger(code: ErrorCode) {
        logError("[${code.name}] ${code.logMessage}")
    }

}