package com.example.truckercore.shared.modules.storage_file.use_cases.implementations

import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.abstractions.UseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.modules.storage_file.mapper.StorageFileMapper
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.CheckStorageFileExistenceUseCase
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.UpdateStorageFileUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

internal class UpdateStorageFileUseCaseImpl(
    private val repository: StorageFileRepository,
    private val checkExistence: CheckStorageFileExistenceUseCase,
    private val validatorService: ValidatorService,
    private val mapper: StorageFileMapper,
    override val permissionService: PermissionService,
    override val requiredPermission: Permission
) : UseCase(permissionService), UpdateStorageFileUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun execute(user: User, file: StorageFile): Flow<Response<Unit>> {
        val id = file.id ?: throw NullPointerException("Null StorageFile id while updating.")

        return checkExistence.execute(user, id).flatMapConcat { response ->
            if (response.isEmpty()) throw ObjectNotFoundException(
                "Attempting to update a StorageFile that was not found for id: $id."
            )
            user.runIfPermitted { processUpdate(file) }
        }
    }

    private fun processUpdate(file: StorageFile): Flow<Response<Unit>> {
        validatorService.validateEntity(file)
        val dto = mapper.toDto(file)
        return repository.update(dto)
    }

}
