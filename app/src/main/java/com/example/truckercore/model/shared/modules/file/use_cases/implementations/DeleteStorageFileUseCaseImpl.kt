package com.example.truckercore.model.shared.modules.file.use_cases.implementations

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.modules.file.repository.FileRepository
import com.example.truckercore.model.shared.modules.file.use_cases.interfaces.CheckFileExistenceUseCase
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class DeleteStorageFileUseCaseImpl(
    override val requiredPermission: Permission,
    override val permissionService: PermissionService,
    private val repository: FileRepository,
    private val checkExistence: CheckFileExistenceUseCase
) : UseCase(permissionService),
    com.example.truckercore.model.shared.modules.file.use_cases.interfaces.DeleteStorageFileUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User, id: String): Flow<Response<Unit>> =
        checkExistence.execute(user, id).flatMapConcat { response ->
            if (response.isEmpty()) throw ObjectNotFoundException(
                "Attempting to delete a StorageFile that was not found for id: $id."
            )
            user.runIfPermitted { repository.delete(id) }
        }

}