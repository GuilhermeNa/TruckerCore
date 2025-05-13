/*
package com.example.truckercore.model.modules._previous_sample.business_central.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.CheckBusinessCentralExistenceUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore._utils.classes.AppResponse
import kotlinx.coroutines.flow.Flow

internal class CheckBusinessCentralExistenceUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: BusinessCentralRepository
) : UseCase(permissionService), CheckBusinessCentralExistenceUseCase {

    override fun execute(user: User, id: String): Flow<AppResponse<Unit>> =
        user.runIfPermitted { repository.entityExists(id) }

}*/
