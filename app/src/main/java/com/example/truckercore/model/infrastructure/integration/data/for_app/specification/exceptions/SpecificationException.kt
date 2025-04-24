package com.example.truckercore.model.infrastructure.integration.data.for_app.specification.exceptions

import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.Specification

/**
 * Exception thrown when a [Specification] is invalid or cannot be interpreted correctly.
 *
 * This exception is typically raised during query construction or interpretation, especially
 * when the specification lacks required data, is incompatible with the current context, or
 * violates expected formats.
 *
 * @param message Optional detail message to describe the specification error.
 *
 * @see Specification
 */
class SpecificationException(message: String? = null, cause: Throwable? = null): Exception(message, cause)
