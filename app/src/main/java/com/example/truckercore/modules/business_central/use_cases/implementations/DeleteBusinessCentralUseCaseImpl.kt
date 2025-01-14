package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.DeleteBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.UnknownErrorException
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class DeleteBusinessCentralUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val entityExists: CheckBusinessCentralExistenceUseCase,
    private val service: PermissionService
) : DeleteBusinessCentralUseCase {

    override suspend fun execute(user: User, id: String): Flow<Response<Unit>> = flow {
        id.validateIsNotBlank(Field.ID.name)

        val result = if (userHasPermission(user)) {
            processRemoval(user, id)
        } else {
            handleUnauthorizedPermission(user, id)
        }

        emit(result)
    }

    private fun userHasPermission(user: User): Boolean =
        service.canPerformAction(user, Permission.DELETE_BUSINESS_CENTRAL)

    private fun handleUnauthorizedPermission(user: User, id: String): Response.Error {
        logWarn("Unauthorized access attempt by user: ${user.id}, for BusinessCentral ID: $id")
        return Response.Error(
            UnauthorizedAccessException(
                "User does not have permission to delete the business central."
            )
        )
    }

    private suspend fun processRemoval(user: User, id: String): Response<Unit> {
        val existsResponse = entityExists(user, id)

        return if (existsResponse is Response.Error) {
            existsResponse
        } else {
            delete(id)
        }
    }

    private suspend fun entityExists(user: User, id: String): Response<Unit> {
        return when (val response = entityExists.execute(user, id).single()) {
            is Response.Success -> Response.Success(Unit)
            is Response.Error -> Response.Error(response.exception)
            is Response.Empty -> Response.Error(UnknownErrorException("Unknown error while removing a Business Central"))
        }
    }

    private suspend fun delete(id: String): Response<Unit> {
        return repository.delete(id).single()
    }

}