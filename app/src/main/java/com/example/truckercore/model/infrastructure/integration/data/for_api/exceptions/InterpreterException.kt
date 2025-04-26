package com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions

import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification

/**
 * Exception thrown when there is an error related to interpreting the [Specification].
 *
 * @param message A descriptive message explaining the interpretation error.
 * @param cause The underlying cause of the exception, if available.
 */
class InterpreterException(message: String? = null, cause: Throwable? = null) :
    DataSourceException(message, cause)