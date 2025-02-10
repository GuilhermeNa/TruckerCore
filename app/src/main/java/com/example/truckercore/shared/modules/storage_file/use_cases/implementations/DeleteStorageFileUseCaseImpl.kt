package com.example.truckercore.shared.modules.storage_file.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.CheckStorageFileExistenceUseCase
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.DeleteStorageFileUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class DeleteStorageFileUseCaseImpl(
    private val repository: StorageFileRepository,
    private val checkExistence: CheckStorageFileExistenceUseCase,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), DeleteStorageFileUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, id: String): Flow<Response<Unit>> =
        checkExistence.execute(user, id).flatMapConcat { response ->
            if (response.isEmpty()) {
                throw ObjectNotFoundException(
                    "Attempting to delete a StorageFile that was not found for id: $id."
                )
            }

            user.runIfPermitted { repository.delete(id) }
        }

}