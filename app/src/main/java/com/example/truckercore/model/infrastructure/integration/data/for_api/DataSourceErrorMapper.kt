package com.example.truckercore.model.infrastructure.integration.data.for_api

import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.DataSourceException
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification

/**
 * Maps backend-specific exceptions into standardized [DataSourceException]s recognized by the application.
 *
 * This interface provides a centralized way to convert raw errors (e.g., network errors, mapping errors,
 * backend-specific exceptions) into custom exceptions that are meaningful and consistent across the app's
 * data access layer.
 *
 * This abstraction ensures the rest of the application doesn't have to deal with backend-specific error types.
 *
 * @see DataSourceException
 * @see Specification
 */
interface DataSourceErrorMapper {

    /**
     * Maps a backend [Throwable] into a domain-specific [DataSourceException], using the context
     * provided by the [Specification].
     *
     * ### Example:
     * ```kotlin
     * class FirestoreErrorMapper : DataSourceErrorMapper {
     *     override fun invoke(e: Throwable, spec: Specification<*>): DataSourceException {
     *         return when (e) {
     *             is ApiNetworkException -> {
     *                  NetworkException("Permission denied", e)
     *             }
     *             ...
     *         }
     *     }
     * }
     * ```
     *
     * @param e The raw exception thrown by the backend.
     * @param spec The [Specification] that was being executed when the error occurred.
     * @return A [DataSourceException] representing a known error type in the application.
     */
    operator fun invoke(e: Throwable, spec: Specification<*>): DataSourceException

}