package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class CheckBusinessCentralExistenceUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val service: PermissionService
) : CheckBusinessCentralExistenceUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Boolean>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result = if (userHasPermission(user)) {
            val response = repository.entityExists(id).single()
            processResponse(response)
        } else handleUnauthorizedPermission(user, id)

        emit(result)

    }.catch {
        val result = handleUnexpectedError(it)
        emit(result)
    }

    private fun userHasPermission(user: User): Boolean =
        service.canPerformAction(user, Permission.VIEW_BUSINESS_CENTRAL)

    private fun processResponse(response: Response<Boolean>): Response<Boolean> {
        return when (response) {
            is Response.Success -> handleSuccessResponse(response)
            is Response.Error -> handleErrorResponse(response)
            is Response.Empty -> handleEmptyResponse(response)
        }
    }

    private fun handleSuccessResponse(response: Response.Success<Boolean>): Response<Boolean> {
        return response
    }

    private fun handleErrorResponse(response: Response.Error): Response.Error {
        logError("Error while handling with database response")
        return response
    }

    private fun handleEmptyResponse(response: Response.Empty): Response.Empty {
        logWarn("The database response came back empty.")
        return response
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

    private fun handleUnauthorizedPermission(user: User, id: String): Response.Error {
        logWarn("Unauthorized access attempt by user: ${user.id}, for BusinessCentral ID: $id")
        return Response.Error(
            exception = UnauthorizedAccessException(
                "User does not have permission to view the business central."
            )
        )
    }

}