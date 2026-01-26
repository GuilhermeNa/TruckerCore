package com.example.truckercore.core.error

import com.example.truckercore.core.error.core.AppException

/**
 * Represents errors related to the presentation layer.
 *
 * These exceptions are typically used to handle UI-specific
 * failures or unexpected presentation states.
 */
sealed class PresentationException(
    message: String? = null,
    cause: Throwable? = null
) : AppException(message, cause) {

    /**
     * Represents an unknown presentation-layer error.
     */
    class Unknown(
        message: String = "An unknown error occurred.",
        cause: Throwable? = null
    ) : PresentationException(message, cause)

}