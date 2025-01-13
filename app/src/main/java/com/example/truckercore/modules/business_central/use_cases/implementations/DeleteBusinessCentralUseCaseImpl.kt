package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.errors.BusinessCentralRemovalException
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.DeleteBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.EntityNotFoundException
import com.example.truckercore.shared.utils.Response
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.single

internal class DeleteBusinessCentralUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val entityExists: CheckBusinessCentralExistenceUseCase,
    private val service: PermissionService
) : DeleteBusinessCentralUseCase {

    override suspend fun execute(user: User, id: String) {
        id.validateIsNotBlank(Field.ID.name)
        validateUserCanDelete(user)

        if (entityExists(user, id)) {
            TODO()
            //return repository.delete(id)
        }

    }

    private fun validateUserCanDelete(user: User) {
        if (!service.canPerformAction(user, Permission.DELETE_BUSINESS_CENTRAL))
            throw UnauthorizedAccessException("User does not have permission to delete the business central.")
    }

    private suspend fun entityExists(user: User, id: String): Boolean {
        return when (val response = entityExists.execute(user, id).single()) {
            is Response.Success -> true
            is Response.Error -> handleErrorResponse(response.exception, id)
            is Response.Empty -> handleEmptyResponse(id)
        }
    }

    private fun handleErrorResponse(e: Exception, id: String): Nothing {
        when (e) {
            is UnauthorizedAccessException -> {
                logError(e.message ?: "User does not have permission to view the business central.")
                throw e
            }

            else -> {
                logError(
                    e.message ?: "Unexpected exception while checking object existence with ID: $id"
                )
                throw BusinessCentralRemovalException(
                    "Unexpected error occurred while removing a Business central.",
                    e
                )
            }
        }
    }

    private fun handleEmptyResponse(id: String): Boolean {
        logError("The BusinessCentral with ID: $id is not found in database and cannot be removed.")
        throw EntityNotFoundException("The BusinessCentral is not found and cannot be removed.")
    }

}