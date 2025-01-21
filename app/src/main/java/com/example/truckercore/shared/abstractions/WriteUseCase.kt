package com.example.truckercore.shared.abstractions

import com.example.truckercore.configs.app_constants.DbOperation
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn

internal abstract class WriteUseCase {

    private val clazz = javaClass.simpleName

    protected fun handleUnauthorizedPermission(
        user: User,
        operation: DbOperation,
        entity: Entity
    ): Response.Error {
        val message =
            "$clazz: Unauthorized access attempt by user: ${user.id}, for ${operation.getName()} an entity: $entity."
        logWarn(message)
        return Response.Error(exception = UnauthorizedAccessException(message))
    }

    protected fun handleNonExistentObject(operation: DbOperation, entity: Entity): Response.Error {
        val message = "$clazz: Trying to ${operation.getName()} a non-persisted entity: $entity."
        logError(message)
        return Response.Error(ObjectNotFoundException(message))
    }

    protected fun handleExistenceCheckageFailure(response: Response<Boolean>): Response<Unit> {
        return if (response is Response.Error) {
            logError("$clazz: Received an error response when checking an entity existence.")
            Response.Error(response.exception)
        } else {
            val message = "$clazz: Received an empty response when checking an entity existence."
            logError(message)
            Response.Error(ObjectNotFoundException(message))
        }
    }

    protected fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("$clazz: An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }


}