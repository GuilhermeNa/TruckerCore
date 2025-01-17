package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CheckUserExistenceUseCaseImpl(
    private val repository: UserRepository,
    private val permissionService: PermissionService
) : CheckUserExistenceUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Boolean>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result =
            if (userHasPermission(user)) processExistenceCheckage(id)
            else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private suspend fun processExistenceCheckage(id: String) =
        when (val response = checkUserExists(id)) {
            is Response.Success -> response
            else -> handleFailureResponse(response)
        }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.VIEW_USER)

    private suspend fun checkUserExists(id: String) = repository.entityExists(id).single()

    private fun handleFailureResponse(response: Response<Boolean>): Response.Error {
        return if (response is Response.Error) {
            logError("${this.javaClass.simpleName}: Received an error response from database")
            response
        } else {
            val message = "The database response came back empty."
            logWarn("${this.javaClass.simpleName}: $message")
            Response.Error(ObjectNotFoundException())
        }
    }

    private fun handleUnauthorizedPermission(user: User, id: String): Response.Error {
        val message = "Unauthorized access attempt by user: ${user.id}, for User ID: $id."
        logWarn("${this.javaClass.simpleName}: $message")
        return Response.Error(exception = UnauthorizedAccessException(message))
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("${this.javaClass.simpleName}: An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}