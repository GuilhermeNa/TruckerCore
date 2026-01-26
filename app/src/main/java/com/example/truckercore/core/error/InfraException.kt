package com.example.truckercore.core.error

import com.example.truckercore.core.error.core.AppException

/**
 * Represents errors related to infrastructure concerns.
 *
 * These exceptions usually involve networking, configuration,
 * or low-level system operations.
 */
sealed class InfraException(
    message: String? = null,
    cause: Throwable? = null
) : AppException(message, cause) {

    /**
     * Represents an unknown infrastructure error.
     */
    class Unknown(
        message: String = "An unknown error occurred.",
        cause: Throwable? = null
    ) : InfraException(message, cause)

    /**
     * Indicates that the network is unavailable.
     */
    class Network(
        message: String = "Network unavailable.",
        cause: Throwable? = null
    ) : InfraException(message, cause)

    /**
     * Indicates an invalid or unexpected outcome.
     */
    class Outcome(
        message: String = "Invalid outcome.",
        cause: Throwable? = null
    ) : InfraException(message, cause)

    /**
     * Indicates an invalid specification or configuration.
     */
    class Specification(
        message: String = "Invalid specification.",
        cause: Throwable? = null
    ) : InfraException(message, cause)

    /**
     * Indicates an invalid instruction or command.
     */
    class Instruction(
        message: String = "Invalid instruction.",
        cause: Throwable? = null
    ) : InfraException(message, cause)
}