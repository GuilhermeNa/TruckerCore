package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.UpdateBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User

internal class UpdateBusinessCentralUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val service: PermissionService
) : UpdateBusinessCentralUseCase {

    private companion object {
        private const val MESSAGE = "User does not have permission to update the business central."
    }

    //TODO("Estratégia de validar o objeto antes de atualiza-lo")
    override suspend fun execute(user: User, dto: BusinessCentralDto) {

        if (userHasPermission(user)) {
            return repository.update(dto)
        } else {
            throw UnauthorizedAccessException(MESSAGE)
        }

    }

    private fun userHasPermission(user: User): Boolean =
        service.canPerformAction(user, Permission.UPDATE_BUSINESS_CENTRAL)

}