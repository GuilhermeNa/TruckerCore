package com.example.truckercore.model.configs.build

/**
 * Exception thrown when a build-time or configuration-related error occurs.
 *
 * Typically used to signal issues related to incorrect application setup,
 * such as unrecognized product flavors, invalid build configuration,
 * or missing metadata required during runtime.
 *
 * @param message Optional detail message describing the error.
 * @param cause Optional underlying cause of the exception.
 */
class BuildException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)