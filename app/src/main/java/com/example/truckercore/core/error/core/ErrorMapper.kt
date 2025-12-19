package com.example.truckercore.core.error.core

/**
 * Generic interface for mapping exceptions from one type to another.
 *
 * Typically used to convert low-level or external exceptions (e.g., network, database, Firebase)
 * into domain-specific or application-specific exceptions ([AppException]) that are meaningful
 * within the application.
 *
 * Implementations of this interface should define the rules for mapping specific
 * exceptions to the appropriate [AppException] subclass.
 *
 * This allows the application to:
 *  - Isolate external libraries from the domain layer
 *  - Standardize error handling
 *  - Provide meaningful error messages or types to the UI or business logic
 */
interface ErrorMapper {

    /**
     * Maps a [Throwable] to a corresponding [AppException].
     *
     * @param e The original exception to be mapped.
     * @return A domain- or application-specific [AppException] representing the error.
     */
    operator fun invoke(e: Throwable): AppException

}