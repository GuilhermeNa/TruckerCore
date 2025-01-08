package com.example.truckercore.modules.business_central.use_cases.implementations

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.interfaces.DeleteBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.expressions.validateIsNotBlank

internal class DeleteBusinessCentralUseCaseImpl(
    private val repository: BusinessCentralRepository,
    private val service: PermissionService
) : DeleteBusinessCentralUseCase {

    private companion object {
        private const val MESSAGE = "User does not have permission to delete the business central."
    }

    override fun execute(user: User, id: String) {
        id.validateIsNotBlank(Field.ID.name)

        if (userHasPermission(user)) {
            return repository.delete(id)
        } else {
            throw UnauthorizedAccessException(MESSAGE)
        }

    }

    private fun userHasPermission(user: User): Boolean =
        service.canPerformAction(user, Permission.DELETE_BUSINESS_CENTRAL)

}