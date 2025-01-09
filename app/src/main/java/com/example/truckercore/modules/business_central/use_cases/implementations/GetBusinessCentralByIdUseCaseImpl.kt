package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.GetBusinessCentralByIdUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.Response
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank
import kotlinx.coroutines.flow.Flow

internal class GetBusinessCentralByIdUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val service: PermissionService
) : GetBusinessCentralByIdUseCase {

    private companion object {
        private const val MESSAGE = "User does not have permission to view the business central."
    }

    override suspend fun execute(user: User, id: String): Flow<Response<BusinessCentralDto>> {
        id.validateIsNotBlank(Field.ID.name)

        if (userHasPermission(user)) {
            return repository.fetchById(id)
        } else {
            throw UnauthorizedAccessException(MESSAGE)
        }

    }

    private fun userHasPermission(user: User): Boolean =
        service.canPerformAction(user, Permission.VIEW_BUSINESS_CENTRAL)

}