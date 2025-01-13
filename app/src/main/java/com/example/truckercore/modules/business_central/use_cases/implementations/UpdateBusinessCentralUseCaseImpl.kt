package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.UpdateBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.expressions.logWarn

internal class UpdateBusinessCentralUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val service: PermissionService,
    private val validator: ValidatorService,
    private val mapper: BusinessCentralMapper
) : UpdateBusinessCentralUseCase {

    override suspend fun execute(user: User, entity: BusinessCentral) {
        if (userHasPermission(user)) {
            processRequest(entity)
        } else handleUnauthorizedPermission(user, entity.id)
    }

    private fun processRequest(entity: BusinessCentral) {
        validator.validateEntity(entity)
        val dto = mapper.toDto(entity)
        repository.update(dto)
    }

    private fun userHasPermission(user: User): Boolean =
        service.canPerformAction(user, Permission.UPDATE_BUSINESS_CENTRAL)

    private fun handleUnauthorizedPermission(user: User, id: String?) {
        logWarn("Unauthorized access attempt by user: ${user.id}, for BusinessCentral ID: $id")
        throw UnauthorizedAccessException(
            "User does not have permission to update the business central."
        )
    }

}