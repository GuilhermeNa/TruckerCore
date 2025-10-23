package com.example.truckercore.core.error

import com.example.truckercore.core.error.core.AppException

sealed class InfraException(
    message: String? = null,
    cause: Throwable? = null
) : AppException(message, cause) {

    class Unknown(
        message: String = "An unknown error occurred.",
        cause: Throwable? = null
    ) : InfraException(message, cause)

    class Network(
        message: String = "Network unavailable.",
        cause: Throwable? = null
    ) : InfraException(message, cause)

    class Outcome(
        message: String = "Invalid outcome.",
        cause: Throwable? = null
    ) : InfraException(message, cause)

    class Specification(
        message: String = "Invalid specification.",
        cause: Throwable? = null
    ) : InfraException(message, cause)

    class Instruction(
        message: String = "Invalid instruction .",
        cause: Throwable? = null
    ) : InfraException(message, cause)

}