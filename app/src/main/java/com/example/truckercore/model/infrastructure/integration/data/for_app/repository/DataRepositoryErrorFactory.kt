package com.example.truckercore.model.infrastructure.integration.data.for_app.repository

import com.example.truckercore.model.errors.AppException
import com.example.truckercore.model.errors.infra.InfraException
import com.example.truckercore.model.errors.infra.error_code.DatabaseErrorCode
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InterpreterException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.InvalidDataException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.MappingException
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.NetworkException
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.contracts.Specification

/**
 * Factory responsible for mapping data layer exceptions into domain-specific error codes ([DataErrorCode]).
 *
 * Each function transforms a raw [Throwable] into a structured error with:
 * - A specific error code ([DataErrorCode])
 * - A user-friendly message
 * - A developer-friendly log message
 *
 * The returned error objects are used consistently throughout the application
 * to ensure unified error handling in UI and logging systems.
 */
class DataRepositoryErrorFactory {

    companion object {
        private const val FIND_ONE_ERR_MSG =
            "Failed to find one entity by specification."
        private const val FIND_ALL_ERR_MSG =
            "Failed to find all entities by specification."
        private const val FLOW_ONE_ERR_MSG =
            "Failed to observe flow for a single entity by specification."
        private const val FLOW_ALL_ERR_MSG =
            "Failed to observe flow for all entities by specification."
    }

    fun findingOne(spec: Specification<*>, t: Throwable): AppException {
        return get(FIND_ONE_ERR_MSG, spec, t)
    }

    fun findingAll(spec: Specification<*>, t: Throwable): AppException {
        return get(FIND_ALL_ERR_MSG, spec, t)
    }

    fun flowingOne(spec: Specification<*>, t: Throwable): AppException {
        return get(FLOW_ONE_ERR_MSG, spec, t)
    }

    fun flowingAll(spec: Specification<*>, t: Throwable): AppException {
        return get(FLOW_ALL_ERR_MSG, spec, t)
    }

    private fun get(message: String, spec: Specification<*>, cause: Throwable): AppException {
        if (cause is NetworkException) return InfraException.NetworkUnavailable()

        val errorCode = when (cause) {
            is InterpreterException -> DatabaseErrorCode.ImplementationError
            is InvalidDataException -> DatabaseErrorCode.ApiError
            is MappingException -> DatabaseErrorCode.ImplementationError
            else -> DatabaseErrorCode.Unknown
        }
        return InfraException.DatabaseError(
            code = errorCode,
            message = "$message $spec",
            cause = cause
        )
    }

}