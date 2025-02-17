package com.example.truckercore.shared.modules.file.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.file.repository.FileRepository
import com.example.truckercore.shared.modules.file.use_cases.interfaces.CheckFileExistenceUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CheckFileExistenceUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: FileRepository
) : UseCase(permissionService), CheckFileExistenceUseCase {

    override fun execute(user: User, id: String): Flow<Response<Unit>> =
        user.runIfPermitted { repository.entityExists(id) }

}