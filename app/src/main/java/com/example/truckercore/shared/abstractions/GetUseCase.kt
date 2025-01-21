package com.example.truckercore.shared.abstractions

import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn

internal abstract class GetUseCase {

    private val clazz = javaClass.simpleName

    protected fun handleFailureResponse(response: Response.Error): Response.Error {
        logError("$clazz: Received an error response from database, error: ${response.exception}")
        return response
    }

    protected fun handleEmptyResponse(response: Response.Empty): Response.Empty {
        logWarn("$clazz: The database response came back empty.")
        return response
    }

    protected fun handleUnauthorizedPermission(
        user: User,
        objName: String,
        objId: String
    ): Response.Error {
        val message =
            "$clazz: Unauthorized access attempt by user: ${user.id}, for $objName ID: $objId."
        logWarn(message)
        return Response.Error(UnauthorizedAccessException(message))
    }

    protected fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("$clazz: An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}