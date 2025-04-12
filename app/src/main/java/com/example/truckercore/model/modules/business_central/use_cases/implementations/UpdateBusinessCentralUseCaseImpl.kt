package com.example.truckercore.model.modules.business_central.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.business_central.entity.BusinessCentral
import com.example.truckercore.model.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.model.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.UpdateBusinessCentralUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class UpdateBusinessCentralUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: BusinessCentralRepository,
    private val checkExistence: CheckBusinessCentralExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: BusinessCentralMapper
) : UseCase(permissionService), UpdateBusinessCentralUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, bCentral: BusinessCentral): Flow<Response<Unit>> {
        val id = bCentral.id ?: throw NullPointerException(
            "Null Business Central id while updating."
        )

        return checkExistence.execute(user, id).flatMapConcat { response ->
            if (response !is Response.Success) throw ObjectNotFoundException(
                "Attempting to update a Business Central that was not found for id: $id."
            )
            user.runIfPermitted { processUpdate(bCentral) }
        }
    }

    private fun processUpdate(entity: BusinessCentral): Flow<Response<Unit>> {
        validatorService.validateEntity(entity)
        val dto = mapper.toDto(entity)
        return repository.update(dto)
    }

}