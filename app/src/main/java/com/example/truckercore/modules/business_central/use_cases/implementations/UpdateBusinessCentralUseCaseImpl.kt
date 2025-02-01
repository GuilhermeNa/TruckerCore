package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.modules.business_central.use_cases.interfaces.UpdateBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
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
) : UseCase(), UpdateBusinessCentralUseCase {

    override suspend fun execute(user: User, entity: BusinessCentral): Flow<Response<Unit>> = flow {
        val result =
            if (userHasPermission(user)) continueForExistenceCheckage(user, entity)
            else handleUnauthorizedPermission(user, entity.id!!)

        emit(result)

    }.catch {
        emit(handleUnexpectedError(it))
    }

    private fun userHasPermission(user: User): Boolean =
        permissionService.canPerformAction(user, Permission.UPDATE_BUSINESS_CENTRAL)

    private suspend fun continueForExistenceCheckage(user: User, entity: BusinessCentral) =
        when (val response = checkExistence.execute(user, entity.id!!).single()) {
            is Response.Success -> processUpdate(entity)
            is Response.Empty -> handleNonExistentObject(entity.id)
            is Response.Error -> handleFailureResponse(response)
        }

    private suspend fun processUpdate(entity: BusinessCentral): Response<Unit> {
        validatorService.validateEntity(entity)
        val dto = mapper.toDto(entity)
        return repository.update(dto).single()
    }

}