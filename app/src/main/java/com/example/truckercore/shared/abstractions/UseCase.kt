package com.example.truckercore.shared.abstractions

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.NewMapper
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Abstract class representing a base use case.
 *
 * This class provides common functionality for use cases, such as handling errors, permission checks,
 * and managing the flow of execution.
 *
 * @see Response
 * @see User
 */
internal abstract class UseCase(
    open val permissionService: PermissionService,
) {

    protected abstract val requiredPermission: Permission

    protected fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, requiredPermission)

    fun <T> User.runIfPermitted(block: () -> Flow<Response<T>>): Flow<Response<T>> {
        return if (userHasPermission(this)) block()
        else throw UnauthorizedAccessException(this, requiredPermission)
    }

    //----------------------------------------------------------------------------------------------

 /*   protected fun <T> fetchData(
        documentParams: DocumentParameters,
        operation: (documentParams: DocumentParameters) -> Flow<Response<T>>
    ): Flow<Response<T>> {
        return when (userHasPermission(documentParams.user)) {
            true -> operation(documentParams)
           *//* false -> logAndReturnDeniedAuth(documentParams.user)*//*
        }
    }*/

/*    protected fun <T> fetchData(
        queryParams: QueryParameters,
        operation: (documentParams: QueryParameters) -> Flow<Response<T>>
    ): Flow<Response<T>> {
        return when (userHasPermission(queryParams.user)) {
            true -> operation(queryParams)
           *//* false -> *//**//*logAndReturnDeniedAuth(queryParams.user)*//*
        }
    }*/

    /**
     * Handles an error response from the repository or service.
     *
     * This method logs the error message and returns the same error response.
     *
     * @param response The error response that was received.
     * @return The same error response.
     */
    protected fun logAndReturnResponse(response: Response.Error): Response.Error {
        logError(
            context = javaClass,
            exception = response.exception,
            message = "Received an error response from database."
        )
        return response
    }

    protected fun logAndReturnResponse(response: Response.Empty): Response.Empty {
        logWarn(
            context = javaClass,
            message = "Received an error response from database."
        )
        return response
    }

/*
    protected fun logAndReturnDeniedAuth(user: User): Flow<Response.Error> {
        val message = "Unauthorized access attempt by user: ${user.id}, for $requiredPermission."
        logWarn(javaClass, message)
        return flowOf(Response.Error(UnauthorizedAccessException(message)))
    }
*/

    /**
     * Handles an unexpected error that occurs during the execution of the use case.
     *
     * This method logs the unexpected error and returns a `Response.Error` containing the exception.
     *
     * @param throwable The unexpected exception that was thrown.
     * @return A `Response.Error` containing the exception.
     */
    protected fun handleUnexpectedError(throwable: Throwable): Response.Error {
        throwable as Exception
        logError(
            context = javaClass,
            exception = throwable,
            message = "An unexpected error occurred during execution."
        )
        return Response.Error(throwable)
    }

/*    *//**
     * Handles an unauthorized access attempt.
     *
     * This method logs the unauthorized access attempt by a user and returns an error response indicating
     * unauthorized access.
     *
     * @param user The user attempting the unauthorized action.
     * @param id The ID of the entity being accessed.
     * @return A `Response.Error` with an `UnauthorizedAccessException`.
     *//*
    protected fun handleUnauthorizedPermission(user: User, id: String): Response.Error {
        val message = "Unauthorized access attempt by user: ${user.id}, for entity ID: $id"
        logWarn(
            context = javaClass,
            message = message
        )
        return Response.Error(UnauthorizedAccessException(message))
    }*/

    /**
     * Handles the case where the object with the provided ID is not found in the database.
     *
     * This method logs the error and returns a `Response.Error` indicating that the object was not found.
     *
     * @param id The ID of the object that was not found.
     * @return A `Response.Error` with an `ObjectNotFoundException`.
     */
    protected fun handleNonExistentObject(id: String): Response.Error {
        val message = "Entity not found with ID: $id."
        logError(
            context = javaClass,
            exception = ObjectNotFoundException(),
            message = "Entity not found with id: $id"
        )
        return Response.Error(ObjectNotFoundException(message))
    }

/*    *//**
     * Handles an unauthorized access attempt by a user when attempting to perform an action
     * that requires specific permissions.
     *
     * @param user The user attempting the unauthorized action.
     * @param missingPermission The permission that the user is missing in order to perform the action.
     * @return A `Response.Error` containing an `UnauthorizedAccessException` indicating that the user
     *         does not have the required permission.
     *//*
    protected fun handleUnauthorizedPermission(
        user: User,
        missingPermission: Permission
    ): Response.Error {
        val message = "Unauthorized access attempt by user: ${user.id}, for $missingPermission."
        logWarn(
            context = javaClass,
            message = message
        )
        return Response.Error(UnauthorizedAccessException(message))
    }*/

}