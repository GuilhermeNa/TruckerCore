package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.UpdateBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class UpdateBusinessCentralUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val checkExistence: CheckBusinessCentralExistenceUseCase,
    private val permissionService: PermissionService,
    private val validatorService: ValidatorService,
    private val mapper: BusinessCentralMapper
) : UpdateBusinessCentralUseCase {

    override suspend fun execute(user: User, entity: BusinessCentral): Flow<Response<Unit>> = flow {
        val result = if(userHasPermission(user)) {
            val response = checkEntityExists(user, entity)
            if (response is Response.Success) {
                handleCheckExistenceSuccess(entity, response)
            } else handleCheckExistenceFailure(response)
        } else handleUnauthorizedPermission(user, entity.id)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.UPDATE_BUSINESS_CENTRAL)

    private suspend fun checkEntityExists(user: User, entity: BusinessCentral) =
        checkExistence.execute(user, entity.id!!).single()

    private suspend fun handleCheckExistenceSuccess(
        entity: BusinessCentral,
        response: Response.Success<Boolean>
    ): Response<Unit> =
        if (response.data) handleExistentObject(entity)
        else handleNonExistentObject(entity)

    private suspend fun handleExistentObject(entity: BusinessCentral): Response<Unit> {
        validatorService.validateEntity(entity)
        val dto = mapper.toDto(entity)
        return repository.update(dto).single()
    }

    private fun handleCheckExistenceFailure(response: Response<Boolean>): Response<Unit> {
        return if (response is Response.Error) {
            logError("${javaClass.simpleName}: Received an error from CheckExistenceUseCase.")
            Response.Error(response.exception)
        } else {
            logError("${javaClass.simpleName}: The CheckExistenceUseCase response came back empty.")
            Response.Empty
        }
    }

    private fun handleNonExistentObject(entity: BusinessCentral): Response.Error {
        logError("${javaClass.simpleName}: Trying to update a non-existent object.")
        return Response.Error(
            ObjectNotFoundException("Trying to update a non-persisted object: $entity")
        )
    }

    private fun handleUnauthorizedPermission(user: User, id: String?): Response.Error {
        logWarn("${javaClass.simpleName}: Unauthorized access attempt by user: ${user.id}, for BusinessCentral ID: $id")
        return Response.Error(UnauthorizedAccessException("User does not have permission to update the business central."))
    }

    private fun handleUnexpectedError(throwable: Throwable): Response.Error {
        logError("${this.javaClass.simpleName}: An unexpected error occurred during execution: $throwable")
        return Response.Error(exception = throwable as Exception)
    }

}