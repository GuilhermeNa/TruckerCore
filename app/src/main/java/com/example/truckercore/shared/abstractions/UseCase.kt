package com.example.truckercore.shared.abstractions

import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn

abstract class UseCase {

    private val className = this.javaClass.simpleName

    protected fun handleFailureResponse(response: Response.Error): Response.Error {
        val message =
            "$className: Received an error response from database: ${response.exception}."
        logError(message)
        return response
    }

    protected fun handleUnexpectedError(throwable: Throwable): Response.Error {
        val message =
            "$className: An unexpected error occurred during execution: ${throwable}."
        logError(message)
        return Response.Error(exception = throwable as Exception)
    }

    protected fun handleUnauthorizedPermission(user: User, id: String): Response.Error {
        val message =
            "$className: Unauthorized access attempt by user: ${user.id}, for entity ID: $id"
        logWarn(message)
        return Response.Error(UnauthorizedAccessException(message))
    }

    protected fun handleNonExistentObject(id: String): Response.Error {
        val message =
            "$className: The object with ID: $id was not found in the database."
        logError(message)
        return Response.Error(ObjectNotFoundException(message))
    }


}