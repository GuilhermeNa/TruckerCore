package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CheckUserExistenceUseCaseImpl(
    private val repository: UserRepository,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), CheckUserExistenceUseCase {

    override fun execute(user: User, id: String): Flow<Response<Unit>> =
        user.runIfPermitted { repository.entityExists(id) }

}