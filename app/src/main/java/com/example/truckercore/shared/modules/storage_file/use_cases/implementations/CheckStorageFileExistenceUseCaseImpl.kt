package com.example.truckercore.shared.modules.storage_file.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.CheckStorageFileExistenceUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

internal class CheckStorageFileExistenceUseCaseImpl(
    private val repository: StorageFileRepository,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), CheckStorageFileExistenceUseCase {

    override fun execute(user: User, id: String): Flow<Response<Unit>> =
        user.runIfPermitted { repository.entityExists(id) }

}